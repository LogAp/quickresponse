package me.jaybios.quickresponse.models;

import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "secure", length = 1, discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
public class Code {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    @Column(unique = true, updatable = false, nullable = false)
    private UUID uuid;

    private String uri;

    public UUID getUuid() {
        return uuid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public QRCode generateQR() {
        return QRCode.from(uri);
    }

    public String generateImage() {
        byte[] qrcode = generateQR().withSize(500, 500).stream().toByteArray();
        return String.format("data:image/png;base64,%s", Base64.encodeBase64String(qrcode));
    }
}
