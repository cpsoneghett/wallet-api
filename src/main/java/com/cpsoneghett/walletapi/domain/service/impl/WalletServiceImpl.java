package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.dto.WalletCreateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.WalletCreateResponseDto;
import com.cpsoneghett.walletapi.domain.entity.Wallet;
import com.cpsoneghett.walletapi.domain.service.WalletService;
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

import java.security.SecureRandom;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private final NetworkParameters networkParameters = TestNet3Params.get();

    @Override
    public WalletCreateResponseDto create(WalletCreateRequestDto requestDto) throws MnemonicException.MnemonicLengthException {

        Wallet wallet = createWalletPublicAndPrivateKey();
        wallet.setEmail(requestDto.email());

        return new WalletCreateResponseDto(wallet.getEmail(), wallet.getPublicAddress(), wallet.getPrivateKey(), wallet.getCreatedAt());
    }

    private Wallet createWalletPublicAndPrivateKey() throws MnemonicException.MnemonicLengthException {
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
        org.bitcoinj.wallet.Wallet wallet = org.bitcoinj.wallet.Wallet.fromSeed(networkParameters, seed, Script.ScriptType.P2PK);
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
