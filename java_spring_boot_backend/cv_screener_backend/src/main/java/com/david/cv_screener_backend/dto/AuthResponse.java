package com.david.cv_screener_backend.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresInMs
) {}