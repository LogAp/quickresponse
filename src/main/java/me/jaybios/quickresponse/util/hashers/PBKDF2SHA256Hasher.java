package me.jaybios.quickresponse.util.hashers;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBKDF2SHA256Hasher {
    public final Integer DEFAULT_INTERATIONS = 1000;
    public final String ALGORITHM = "pbkdf2_sha256";

    public String getEncodedHash(String password, String salt, int iterations) {
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            System.exit(1);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.err.println("Could NOT generate secret key");
            e.printStackTrace();
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    public String encode(String password, String salt, int iterations) {
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s$%d$%s$%s", ALGORITHM, iterations, salt, hash);
    }

    public String encode(String password, String salt) {
        return this.encode(password, salt, this.DEFAULT_INTERATIONS);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }
}
