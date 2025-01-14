package com.cpsoneghett.walletapi.integrated;

import com.cpsoneghett.walletapi.domain.entity.GlobalParameter;
import com.cpsoneghett.walletapi.domain.service.GlobalParameterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GlobalParameterIntegratedTest {

    @Autowired
    private GlobalParameterService globalParameterService;


    @Test
    public void testGetValueByKey() {
        String key = "COIN_CAP_BASE_URL";
        String expectedValue = "https://api.coincap.io/v2/";

        String actualValue = globalParameterService.getValueByKey(key);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testUpdateExistingParameter() {
        String key = "COIN_CAP_BASE_URL";
        String newValue = "https://new.api.coincap.io/v2/";

        Optional<GlobalParameter> updatedParameter = globalParameterService.update(key, newValue);

        Assertions.assertTrue(updatedParameter.isPresent());
        Assertions.assertEquals(newValue, updatedParameter.get().getValue());
    }

    @Test
    public void testUpdateNonExistingParameter() {
        String key = "NON_EXISTENT_KEY";
        String value = "Some Value";

        Optional<GlobalParameter> result = globalParameterService.update(key, value);

        Assertions.assertFalse(result.isPresent());
    }
}
