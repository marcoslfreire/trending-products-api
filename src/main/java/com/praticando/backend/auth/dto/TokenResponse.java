package com.praticando.backend.auth.dto;

public record TokenResponse(String token, long expiresIn) {
}