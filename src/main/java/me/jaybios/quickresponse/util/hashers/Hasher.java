package me.jaybios.quickresponse.util.hashers;

public interface Hasher {
    String encode(String value, String salt);
    boolean verify(String value, String hashedValue);
}
