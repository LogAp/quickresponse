package me.jaybios.quickresponse.models;

import me.jaybios.quickresponse.models.listeners.SecureListener;
import me.jaybios.quickresponse.util.hashers.Hasher;
import me.jaybios.quickresponse.util.hashers.PBKDF2SHA256Hasher;
import net.glxn.qrgen.javase.QRCode;

import javax.persistence.*;

@Entity
@EntityListeners(SecureListener.class)
@ExcludeSuperclassListeners
@DiscriminatorValue("1")
public class SecureCode extends Code implements Secure {
    private String password;

    @Transient
    private Hasher hasher = new PBKDF2SHA256Hasher();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Hasher getHasher() {
        return hasher;
    }

    @Override
    public QRCode generateQR() {
        String scheme = System.getenv("APPLICATION_SCHEME");

        if (scheme == null)
            scheme = "http";

        String applicationUrl = System.getenv("APPLICATION_URL");

        if (applicationUrl == null)
            applicationUrl = "localhost";

        String uri = String.format("%s://%s/unlock?id=%s", scheme, applicationUrl, getUuid().toString());
        return QRCode.from(uri);
    }
}
