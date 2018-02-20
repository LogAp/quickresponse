package me.jaybios.quickresponse.models;

import me.jaybios.quickresponse.models.listeners.SecureListener;
import me.jaybios.quickresponse.util.hashers.Hasher;
import me.jaybios.quickresponse.util.hashers.PBKDF2SHA256Hasher;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(SecureListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "admin", length = 1, discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
@Table(name = "\"user\"")
public class User implements Secure, Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "pg-uuid")
    @Column(unique = true, updatable = false, nullable = false)
    private UUID uuid;

    @Pattern(regexp = "^[_A-Za-z0-9-]{5,25}", message = "{register.username.pattern}")
    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @Email(message = "{register.email.pattern}")
    @NotNull
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
    @Column(nullable = false)
    private String password;

    private boolean active;

    @Transient
    private Hasher hasher = new PBKDF2SHA256Hasher();

    public UUID getUuid() {
        return uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User() {
        super();
        active = false;
    }
}
