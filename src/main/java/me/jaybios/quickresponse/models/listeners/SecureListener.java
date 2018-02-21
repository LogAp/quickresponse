package me.jaybios.quickresponse.models.listeners;

import me.jaybios.quickresponse.models.Secure;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.security.SecureRandom;
import java.util.Random;

public class SecureListener {
    @PreUpdate
    public void securePrePersist(Object object) {
        Secure secureObject = (Secure)object;
        if (secureObject.isSecure())
            hashPassword(secureObject);
    }

    private void hashPassword(Secure secure) {
        Random random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        secure.setPassword(secure.getHasher().encode(secure.getPassword(), Base64.encodeBase64String(salt)));
    }
}
