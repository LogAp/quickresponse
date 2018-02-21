package me.jaybios.quickresponse.models;

import me.jaybios.quickresponse.util.hashers.Hasher;

public interface Secure {
    String getPassword();
    void setPassword(String password);
    Hasher getHasher();
    boolean isSecure();
}
