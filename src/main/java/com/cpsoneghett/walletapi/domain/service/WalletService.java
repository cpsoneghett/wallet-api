package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.entity.Wallet;
import org.bitcoinj.crypto.MnemonicException;

public interface WalletService {

    public Wallet create() throws MnemonicException.MnemonicLengthException;
}
