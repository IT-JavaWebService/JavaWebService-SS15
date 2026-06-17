package com.example.smartretail.dto;

public class AuthResponse {
    private String message;
    private long timestamp;

    public AuthResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}