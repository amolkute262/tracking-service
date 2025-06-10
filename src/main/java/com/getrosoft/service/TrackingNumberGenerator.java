package com.getrosoft.service;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@Component
public class TrackingNumberGenerator {
    public String generateTrackingNumber(String origin, String destination, UUID customerId, String customerSlug, long counter) {
        String input = origin + destination + customerId + customerSlug + counter;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hash.length && sb.length() < 16; i++) {
                int b = Byte.toUnsignedInt(hash[i]) % 36;
                sb.append(b < 10 ? (char) ('0' + b) : (char) ('A' + b - 10));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate hash", e);
        }
    }
}