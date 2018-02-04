package me.jaybios.quickresponse.models;

import me.jaybios.quickresponse.models.listeners.SecureCodeListener;
import me.jaybios.quickresponse.util.hashers.PBKDF2SHA256Hasher;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ExcludeSuperclassListeners;
import java.security.SecureRandom;
import java.util.Random;

@Entity
@EntityListeners(SecureCodeListener.class)
@ExcludeSuperclassListeners
@DiscriminatorValue("1")
public class SecureCode extends Code {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String otherPassword) {
        PBKDF2SHA256Hasher hasher = new PBKDF2SHA256Hasher();
        return hasher.checkPassword(otherPassword, password);
    }

    public void hashPassword() {
        Random random = new SecureRandom();
        PBKDF2SHA256Hasher hasher = new PBKDF2SHA256Hasher();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        password = hasher.encode(password, Base64.encodeBase64String(salt));
    }

    @Override
    public QRCode generateQR() {
        String scheme = System.getenv("APPLICATION_SCHEME");;

        if (scheme == null)
            scheme = "http";

        String applicationUrl = System.getenv("APPLICATION_URL");

        if (applicationUrl == null)
            applicationUrl = "localhost";

        String uri = String.format("%s://%s/unlock?id=%s", scheme, applicationUrl, getUuid().toString());
        return QRCode.from(uri);
    }
}
