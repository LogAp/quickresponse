package me.jaybios.quickresponse.models.listeners;

import me.jaybios.quickresponse.models.SecureCode;

import javax.persistence.PrePersist;

public class SecureCodeListener {
    @PrePersist
    public void secureCodePrePersist(SecureCode secureCode) {
        secureCode.hashPassword();
    }
}
