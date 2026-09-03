package com.praticando.backend.auth;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.praticando.backend.auth.dto.TokenResponse;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    @ConfigProperty(name = "jwt.duration.seconds", defaultValue = "3600")
    long durationSeconds;

    @Override
    public TokenResponse issueToken(String username) {
        String token = Jwt.issuer("https://trending-products.local/issuer")
                .upn(username)
                .groups(Set.of("user"))
                .expiresIn(Duration.ofSeconds(durationSeconds))
                .sign();
        return new TokenResponse(token, durationSeconds);
    }

}
