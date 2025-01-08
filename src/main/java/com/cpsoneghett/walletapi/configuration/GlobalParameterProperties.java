package com.cpsoneghett.walletapi.configuration;

import com.cpsoneghett.walletapi.domain.entity.GlobalParameter;
import com.cpsoneghett.walletapi.domain.repository.GlobalParameterRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GlobalParameterProperties {

    private GlobalParameterRepository globalParameterRepository;

    public GlobalParameterProperties(GlobalParameterRepository globalParameterRepository) {
        this.globalParameterRepository = globalParameterRepository;
    }

    @PostConstruct
    public void loadGlobalConfig() {

        List<GlobalParameter> configs = globalParameterRepository.findAll();

        configs.forEach(config ->
                System.getProperties().put(config.getKey(), config.getValue()));
    }
    public String getPropertyValue(String key) {
        return System.getProperty(key);
    }
}
