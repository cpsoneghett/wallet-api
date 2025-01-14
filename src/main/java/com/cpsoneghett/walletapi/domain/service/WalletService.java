package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.dto.*;
import com.cpsoneghett.walletapi.domain.enums.OperationType;
import org.bitcoinj.crypto.MnemonicException;

public interface WalletService {

    WalletCreateResponseDto create(WalletCreateRequestDto request) throws MnemonicException.MnemonicLengthException;
    WalletResponseDto addAsset(AssetRequestDto request, OperationType operationType);
    WalletResponseDto get(String privateKey);
    WalletEvaluationResponseDto evaluate(String assets);
}
