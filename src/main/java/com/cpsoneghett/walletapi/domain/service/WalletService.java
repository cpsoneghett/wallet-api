package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.dto.WalletCreateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.WalletCreateResponseDto;
import org.bitcoinj.crypto.MnemonicException;

public interface WalletService {

    WalletCreateResponseDto create(WalletCreateRequestDto request) throws MnemonicException.MnemonicLengthException;
}
