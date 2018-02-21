package me.jaybios.quickresponse.models;

import me.jaybios.quickresponse.models.listeners.SecureListener;
import me.jaybios.quickresponse.util.hashers.Hasher;
import me.jaybios.quickresponse.util.hashers.PBKDF2SHA256Hasher;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EntityListeners(SecureListener.class)
public class Code implements Secure {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    @Column(unique = true, updatable = false, nullable = false)
    private UUID uuid;

    private String uri;

    private boolean secure;

    @ManyToOne
    private User user;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public QRCode generateQR() {
        if (isSecure()) {
            String scheme = System.getenv("APPLICATION_SCHEME");

            if (scheme == null)
                scheme = "http";

            String applicationUrl = System.getenv("APPLICATION_URL");

            if (applicationUrl == null)
                applicationUrl = "localhost";

            String uri = String.format("%s://%s/unlock?id=%s", scheme, applicationUrl, getUuid().toString());
            return QRCode.from(uri);
        }
        return QRCode.from(uri);
    }

    public String generateImage() {
        byte[] qrcode = generateQR().withSize(500, 500).stream().toByteArray();
        return String.format("data:image/png;base64,%s", Base64.encodeBase64String(qrcode));
    }
}
