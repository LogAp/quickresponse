package me.jaybios.quickresponse.models;

import me.jaybios.quickresponse.models.listeners.SecureListener;
import me.jaybios.quickresponse.util.hashers.Hasher;
import me.jaybios.quickresponse.util.hashers.PBKDF2SHA256Hasher;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(SecureListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "admin", length = 1, discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
public class User implements Secure {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    @Column(unique = true, updatable = false, nullable = false)
    private UUID uuid;

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
    @Column(unique = true, nullable = false)
    private String email;

    /* Pattern:
     * At least 8 chars;
     * Contains at least one digit;
     * Contains at least one alpha char and one upper alpha char;
     * Contain at least one special char.
     * Does not contain spaces or tabs.
     */
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Code> codes;

    @Transient
    private Hasher hasher = new PBKDF2SHA256Hasher();

    public UUID getUuid() {
        return uuid;
    }

    public List<Code> getCodes() {
        return codes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCodes(List<Code> codes) {
        this.codes = codes;
    }

    public void addCode(Code code) {
        codes.add(code);
    }

    @Override
    public Hasher getHasher() {
        return hasher;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
