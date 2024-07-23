package com.invoice.demo_inv.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    public String issue(long uid, String email, String firstName) {
        return JWT.create()
                .withSubject(String.valueOf(uid))
                .withExpiresAt(Instant.now().plus(Duration.of(30, ChronoUnit.MINUTES)))
                .withClaim("e", email)
                .withClaim("fn", firstName)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
