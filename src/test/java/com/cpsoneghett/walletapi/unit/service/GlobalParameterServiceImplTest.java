package com.cpsoneghett.walletapi.unit.service;

import com.cpsoneghett.walletapi.domain.entity.GlobalParameter;
import com.cpsoneghett.walletapi.domain.repository.GlobalParameterRepository;
import com.cpsoneghett.walletapi.domain.service.impl.GlobalParameterServiceImpl;
import com.cpsoneghett.walletapi.exception.GlobalParameterNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalParameterServiceImplTest {

    @Mock
    private GlobalParameterRepository globalParameterRepository;

    @InjectMocks
    private GlobalParameterServiceImpl globalParameterService;

    @Test
    void testGetValueByKeySuccess() {
        String key = "testKey";
        String value = "testValue";
        GlobalParameter parameter = new GlobalParameter(key, value);

        when(globalParameterRepository.findById(key)).thenReturn(Optional.of(parameter));

        String result = globalParameterService.getValueByKey(key);

        assertEquals(value, result, "The value should match the parameter's value.");
        verify(globalParameterRepository, times(1)).findById(key);
    }

    @Test
    void testGetValueByKeyNotFound() {
        String key = "nonExistentKey";

        when(globalParameterRepository.findById(key)).thenReturn(Optional.empty());

        GlobalParameterNotFoundException exception = assertThrows(
                GlobalParameterNotFoundException.class,
                () -> globalParameterService.getValueByKey(key),
                "Expected an exception for a non-existent key."
        );

        assertEquals("GlobalParameter - " + key + " - not found!", exception.getMessage());
        verify(globalParameterRepository, times(1)).findById(key);
    }

    @Test
    void testUpdateSuccess() {
        String key = "updateKey";
        String value = "newValue";
        GlobalParameter parameter = new GlobalParameter(key, "oldValue");

        when(globalParameterRepository.findById(key)).thenReturn(Optional.of(parameter));
        when(globalParameterRepository.save(parameter)).thenReturn(parameter);

        Optional<GlobalParameter> updatedParameter = globalParameterService.update(key, value);

        assertTrue(updatedParameter.isPresent(), "Updated parameter should be present.");
        assertEquals(value, updatedParameter.get().getValue(), "The value should be updated.");
        verify(globalParameterRepository, times(1)).findById(key);
        verify(globalParameterRepository, times(1)).save(parameter);
    }

    @Test
    void testUpdateKeyNotFound() {
        String key = "nonExistentKey";
        String value = "newValue";

        when(globalParameterRepository.findById(key)).thenReturn(Optional.empty());

        Optional<GlobalParameter> result = globalParameterService.update(key, value);

        assertTrue(result.isEmpty(), "Result should be empty for a non-existent key.");
        verify(globalParameterRepository, times(1)).findById(key);
        verify(globalParameterRepository, never()).save(any(GlobalParameter.class));
    }

    @Test
    void testValidateKeyThrowsExceptionForNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> globalParameterService.getValueByKey(null),
                "Expected an exception for a null key."
        );

        assertEquals("Key cannot be null or blank.", exception.getMessage());
    }

    @Test
    void testValidateKeyThrowsExceptionForBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> globalParameterService.getValueByKey(" "),
                "Expected an exception for a blank key."
        );

        assertEquals("Key cannot be null or blank.", exception.getMessage());
    }

    @Test
    void testValidateValueThrowsExceptionForNull() {
        String key = "validKey";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> globalParameterService.update(key, null),
                "Expected an exception for a null value."
        );

        assertEquals("Value cannot be null or blank.", exception.getMessage());
    }

    @Test
    void testValidateValueThrowsExceptionForBlank() {
        String key = "validKey";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> globalParameterService.update(key, " "),
                "Expected an exception for a blank value."
        );

        assertEquals("Value cannot be null or blank.", exception.getMessage());
    }
}
