package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.entity.GlobalParameter;
import com.cpsoneghett.walletapi.domain.repository.GlobalParameterRepository;
import com.cpsoneghett.walletapi.domain.service.GlobalParameterService;
import com.cpsoneghett.walletapi.exception.GlobalParameterNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalParameterServiceImpl implements GlobalParameterService {

    private static final Logger logger = LoggerFactory.getLogger(GlobalParameterServiceImpl.class);
    private final GlobalParameterRepository globalParameterRepository;

    public GlobalParameterServiceImpl(GlobalParameterRepository globalParameterRepository) {
        this.globalParameterRepository = globalParameterRepository;
    }

    @Override
    public String getValueByKey(String key) {
        validateKey(key);

        logger.info("Fetching value for key: {}", key);
        return globalParameterRepository.findById(key)
                .map(GlobalParameter::getValue)
                .orElseThrow(() -> {
                    logger.error("GlobalParameter - {} - not found!", key);
                    return new GlobalParameterNotFoundException("GlobalParameter - " + key + " - not found!");
                });
    }

    @Override
    @Transactional
    public Optional<GlobalParameter> update(String key, String value) {
        validateKey(key);
        validateValue(value);

        logger.info("Attempting to update key: {} with value: {}", key, value);
        return globalParameterRepository.findById(key)
                .map(parameter -> {
                    logger.debug("Key: {} found. Updating value to: {}", key, value);
                    parameter.setValue(value);
                    GlobalParameter updatedParameter = globalParameterRepository.save(parameter);
                    logger.info("Successfully updated key: {} with new value: {}", key, updatedParameter.getValue());
                    return updatedParameter;
                })
                .or(() -> {
                    logger.warn("Key: {} not found. Update operation skipped.", key);
                    return Optional.empty();
                });
    }

    private void validateKey(String key) {
        if (key == null || key.isBlank()) {
            logger.error("Validation failed: Key is null or blank.");
            throw new IllegalArgumentException("Key cannot be null or blank.");
        }
    }

    private void validateValue(String value) {
        if (value == null || value.isBlank()) {
            logger.error("Validation failed: Value is null or blank.");
            throw new IllegalArgumentException("Value cannot be null or blank.");
        }
    }
}
