package me.jaybios.quickresponse.util.hashers;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBKDF2SHA256Hasher implements Hasher, Serializable {
    private String getEncodedHash(String value, String salt, int iterations) {
        SecretKeyFactory keyFactory;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            return null;
        }
        KeySpec keySpec = new PBEKeySpec(value.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.err.println("Could NOT generate secret key");
            e.printStackTrace();
            return null;
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    private String encode(String value, String salt, int iterations) {
        String hash = getEncodedHash(value, salt, iterations);
        return String.format("%s$%d$%s$%s", "pbkdf2_sha256", iterations, salt, hash);
    }

    public String encode(String value, String salt) {
        return this.encode(value, salt, 1000);
    }

    public boolean verify(String value, String hashedValue) {
        String[] parts = hashedValue.split("\\$");
        if (parts.length != 4) {
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(value, salt, iterations);

        return hash.equals(hashedValue);
    }
}
