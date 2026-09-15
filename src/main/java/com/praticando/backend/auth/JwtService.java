package com.praticando.backend.auth;

import com.praticando.backend.dto.TokenResponse;


public interface JwtService {

    TokenResponse issueToken(String username);
}
