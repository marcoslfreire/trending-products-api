package com.praticando.backend.auth;

import com.praticando.backend.auth.dto.TokenResponse;


public interface JwtService {

    TokenResponse issueToken(String username);
}
