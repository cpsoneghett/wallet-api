package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.entity.GlobalParameter;

import java.util.Optional;

public interface GlobalParameterService {

    String getValueByKey(String key);

    Optional<GlobalParameter> update(String key, String value);
}
