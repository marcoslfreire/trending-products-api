package com.praticando.backend.dto;

public record TokenResponse(String token, long expiresIn) {
}