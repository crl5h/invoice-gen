package com.invoice.demo_inv.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
@Builder
public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    /**
     * authentication token object for the current user principal
     * 
     * @param principal user principal
     */
    public UserPrincipalAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return principal;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }
}
