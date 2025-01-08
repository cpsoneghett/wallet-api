package com.cpsoneghett.walletapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GLOBAL_PARAMETER")
public class GlobalParameter {

    @Id
    @Column(name = "gp_key")
    private String key;

    @Column(name = "gp_value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
