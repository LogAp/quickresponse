package me.jaybios.quickresponse.models.listeners;

import me.jaybios.quickresponse.models.Secure;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.PrePersist;
import java.security.SecureRandom;
import java.util.Random;

public class SecureListener {
    @PrePersist
    public void securePrePersist(Object secure) {
        hashPassword((Secure)secure);
    }

    private void hashPassword(Secure secure) {
        Random random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        secure.setPassword(secure.getHasher().encode(secure.getPassword(), Base64.encodeBase64String(salt)));
    }
}
