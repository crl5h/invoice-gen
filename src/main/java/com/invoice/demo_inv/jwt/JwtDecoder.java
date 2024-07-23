package com.invoice.demo_inv.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;

    /**
     * Decodes the jwt from the client and verifies it to extract valuable
     * information
     * 
     * @param token JWT token string to be decoded and verified
     * @return DecodedJWT object from auth0
     * @throws JWTVerificationException If the JWT verification fails due to an
     *                                  invalid token or signature.
     */
    public DecodedJWT decode(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .build()
                .verify(token);
    }
}
