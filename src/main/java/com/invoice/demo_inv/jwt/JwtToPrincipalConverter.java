package com.invoice.demo_inv.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.invoice.demo_inv.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    /**
     * Extracts info from jwt and converts it into an
     * userPrincipal(id,email,username,authorities) type object
     * 
     * @return UserPrincipal object extracted from decodedJWT
     */
    public UserPrincipal convertToPrincipal(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .userId(jwt.getClaim("id").asInt())
                .email(jwt.getClaim("e").asString())
                .first_name(jwt.getClaim("fn").asString())
                .last_name(jwt.getClaim("ln").asString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }

    /**
     * Extracts user authorities from a JWT claim.
     * 
     * @param jwt The DecodedJWT representing the decoded JWT containing user
     *            authorities.
     * @return A list of SimpleGrantedAuthority objects extracted from the JWT
     *         claim, or an empty list if the claim is missing or null.
     */
    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        Claim claim = jwt.getClaim("a");
        if (claim.isNull() || claim.isMissing()) {
            return List.of();
        }
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
