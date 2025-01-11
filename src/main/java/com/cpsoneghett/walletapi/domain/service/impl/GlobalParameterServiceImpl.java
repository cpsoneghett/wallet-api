package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.entity.GlobalParameter;
import com.cpsoneghett.walletapi.domain.repository.GlobalParameterRepository;
import com.cpsoneghett.walletapi.domain.service.GlobalParameterService;
import com.cpsoneghett.walletapi.exception.GlobalParameterNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalParameterServiceImpl implements GlobalParameterService {

    private final GlobalParameterRepository globalParameterRepository;

    public GlobalParameterServiceImpl(GlobalParameterRepository globalParameterRepository) {
        this.globalParameterRepository = globalParameterRepository;
    }

    @Override
    public String getValueByKey(String key) {
        Optional<GlobalParameter> value = globalParameterRepository.findById(key);

        if (value.isEmpty()) throw new GlobalParameterNotFoundException("GlobalParameter - " + key + " - not found!");

        return value.get().getValue();
    }

    @Override
    public Optional<GlobalParameter> update(String key, String value) {

        Optional<GlobalParameter> parameter = globalParameterRepository.findById(key);

        if (parameter.isPresent()) {
            parameter.get().setValue(value);
            return Optional.of(globalParameterRepository.save(parameter.get()));
        }

        return Optional.empty();
    }
}
