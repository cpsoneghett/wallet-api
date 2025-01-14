package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.dto.*;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;
import com.cpsoneghett.walletapi.domain.entity.Asset;
import com.cpsoneghett.walletapi.domain.entity.AssetHistory;
import com.cpsoneghett.walletapi.domain.entity.Token;
import com.cpsoneghett.walletapi.domain.entity.Wallet;
import com.cpsoneghett.walletapi.domain.enums.OperationType;
import com.cpsoneghett.walletapi.domain.repository.AssetHistoryRepository;
import com.cpsoneghett.walletapi.domain.repository.AssetRepository;
import com.cpsoneghett.walletapi.domain.repository.TokenRepository;
import com.cpsoneghett.walletapi.domain.repository.WalletRepository;
import com.cpsoneghett.walletapi.domain.service.CoinCapService;
import com.cpsoneghett.walletapi.domain.service.WalletService;
import com.cpsoneghett.walletapi.exception.AssetNotFoundException;
import com.cpsoneghett.walletapi.exception.TokenNotFoundException;
import com.cpsoneghett.walletapi.exception.WalletNotFoundException;
import jakarta.transaction.Transactional;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TokenRepository tokenRepository;
    private final AssetRepository assetRepository;
    private final AssetHistoryRepository assetHistoryRepository;

    private final CoinCapService coinCapService;

    private final NetworkParameters networkParameters = TestNet3Params.get();

    public WalletServiceImpl(WalletRepository walletRepository, TokenRepository tokenRepository, AssetRepository assetRepository, AssetHistoryRepository assetHistoryRepository, CoinCapService coinCapService) {
        this.walletRepository = walletRepository;
        this.tokenRepository = tokenRepository;
        this.assetRepository = assetRepository;
        this.assetHistoryRepository = assetHistoryRepository;
        this.coinCapService = coinCapService;
    }

    @Override
    @Transactional
    public WalletCreateResponseDto create(WalletCreateRequestDto requestDto) throws MnemonicException.MnemonicLengthException {

        // TODO: Validate Request
        if(requestDto == null || requestDto.email() == null || requestDto.email().isEmpty())
            throw new IllegalArgumentException("Request is invalid");

        Wallet wallet = createWalletPublicAndPrivateKey();
        wallet.setEmail(requestDto.email());

        wallet = walletRepository.save(wallet);

        return new WalletCreateResponseDto(wallet.getEmail(), wallet.getPublicAddress(), wallet.getPrivateKey(), wallet.getCreatedAt());
    }

    @Override
    @Transactional
    public WalletResponseDto addAsset(AssetRequestDto request, OperationType operationType) {

        Wallet wallet = walletRepository.findByPrivateKey(request.privateKey())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        Token token = tokenRepository.findBySearchParam(request.token())
                .orElseThrow(() -> new TokenNotFoundException("Token " + request.token() + " not found"));

        Asset asset = operationType == OperationType.CREATE
                ? createAsset(wallet, token, request.amount())
                : updateAsset(wallet, token, request.amount());

        updateWalletValues(wallet);
        saveAssetHistory(asset);

        return buildWalletResponse(wallet);
    }

    private Asset createAsset(Wallet wallet, Token token, BigDecimal amount) {
        Asset asset = new Asset();
        asset.setToken(token);
        asset.setQuantity(amount);
        asset.setPrice(token.getPriceUsd());
        asset.setValue(amount.multiply(token.getPriceUsd()));
        asset.setWallet(wallet);

        wallet.addAsset(asset);
        assetRepository.save(asset);
        return asset;
    }

    private Asset updateAsset(Wallet wallet, Token token, BigDecimal amount) {
        Asset asset = assetRepository.findByWalletAndToken(wallet.getId(), token.getId())
                .orElseThrow(() -> new AssetNotFoundException(
                        "Asset " + token.getIdName() + " for this wallet was not found."));

        asset.setQuantity(asset.getQuantity().add(amount));
        asset.setPrice(token.getPriceUsd());
        asset.setValue(asset.getQuantity().multiply(asset.getPrice()));
        assetRepository.save(asset);
        return asset;
    }

    private void updateWalletValues(Wallet wallet) {
        BigDecimal totalValue = wallet.getAssets()
                .stream()
                .map(a -> a.getQuantity().multiply(a.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        wallet.setTotal(totalValue);
        walletRepository.save(wallet);
    }

    private void saveAssetHistory(Asset asset) {
        AssetHistory history = new AssetHistory(asset.getId(), asset.getQuantity(), asset.getPrice());
        assetHistoryRepository.save(history);
    }

    private WalletResponseDto buildWalletResponse(Wallet wallet) {
        return new WalletResponseDto(wallet.getPublicAddress(), wallet.getTotal(), wallet.assetsToDto());
    }

    @Override
    public WalletResponseDto get(String privateKey) {

        // TODO: Improvements: Possible security validations here
        Optional<Wallet> wallet = walletRepository.findByPrivateKey(privateKey);

        if (wallet.isEmpty())
            throw new WalletNotFoundException("Wallet not found");

        return new WalletResponseDto(wallet.get().getPublicAddress(), wallet.get().getTotal(), wallet.get().assetsToDto());
    }

    @Override
    public WalletEvaluationResponseDto evaluate(String assets) {

        if (assets == null || assets.isBlank()) {
            throw new IllegalArgumentException("Assets input cannot be null or empty");
        }

        String[] lines = assets.split("\n");

        BigDecimal total = BigDecimal.ZERO;
        String bestAsset = null;
        BigDecimal biggestPercentage = BigDecimal.ZERO;
        String worstAsset = null;
        BigDecimal lowestPercentage = BigDecimal.valueOf(Double.MAX_VALUE);

        for (String line : lines) {
            try {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid input format: " + line);
                }

                String symbol = parts[0].trim();
                BigDecimal amount = new BigDecimal(parts[1].trim());
                BigDecimal avgPrice = new BigDecimal(parts[2].trim());

                Token token = tokenRepository.findBySearchParam(symbol)
                        .orElseThrow(() -> new IllegalArgumentException("Token not found for symbol: " + symbol));

                TokenSingleResponseDto coinCapToken = coinCapService.getAssetById(token.getIdName());

                BigDecimal currentPrice = new BigDecimal(coinCapToken.data().priceUsd());
                BigDecimal currentValue = amount.multiply(currentPrice);
                total = total.add(currentValue);

                BigDecimal percentageEvaluation = calculatePercentageEvaluation(currentPrice, avgPrice);

                if (percentageEvaluation.compareTo(biggestPercentage) > 0) {
                    bestAsset = coinCapToken.data().symbol();
                    biggestPercentage = percentageEvaluation;
                }

                if (percentageEvaluation.compareTo(lowestPercentage) < 0) {
                    worstAsset = coinCapToken.data().symbol();
                    lowestPercentage = percentageEvaluation;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new WalletEvaluationResponseDto(total, bestAsset, biggestPercentage, worstAsset, lowestPercentage);
    }

    private BigDecimal calculatePercentageEvaluation(BigDecimal currentPrice, BigDecimal avgPrice) {
        return currentPrice
                .divide(avgPrice, MathContext.DECIMAL128)
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, MathContext.DECIMAL128.getRoundingMode());
    }


    /* This method was just researched and adapted from a reference in the internet to create public and private key */
    public Wallet createWalletPublicAndPrivateKey() throws MnemonicException.MnemonicLengthException {
        /*
         * Secure random: this gives us a random numbers, it returns a byte array,
         * 1 byte = 8bits
         * 16 bytes = 128
         *  dividing 128 by 12 will give you 12 mnemonic codes
         *  it is also called entropy
         */
        byte[] randomness = SecureRandom.getSeed(16);
        /*
         * From the documentation of toMnemonic,
         * it picks the words from mnemonic/wordlist/english.txt in the bitcoinj j file
         */
        List<String> mnemonicCode = MnemonicCode.INSTANCE.toMnemonic(randomness);
        /*
         * At this point an Elongation Algorithm is employed to make it up to 512bits
         */
        DeterministicSeed seed = new DeterministicSeed(mnemonicCode, null, "", System.currentTimeMillis());
        /*
         * Here a wallet is created from the seed
         * a P2PKHWallet is always created when .formseed() method is used
         */
        // Wallet wallet = Wallet.fromSeed(networkParameters, seed);
        org.bitcoinj.wallet.Wallet wallet = org.bitcoinj.wallet.Wallet.fromSeed(networkParameters, seed, Script.ScriptType.P2PKH);
        /*
         * Get the first key in the wallet (you can generate more keys as needed)
         */
        ECKey key = wallet.freshReceiveKey();

        // Create a P2PKH address using the public key
        Address address = LegacyAddress.fromKey(networkParameters, key);

        // Get the private key in Wallet Import Format (WIF)
        String privateKeyWIF = key.getPrivateKeyEncoded(networkParameters).toBase58();

        return new Wallet(address.toString(), privateKeyWIF);
    }

}
