package com.invoice.demo_inv.service;

import com.invoice.demo_inv.utils.LoginResponse;
import com.invoice.demo_inv.jwt.JwtIssuer;
import com.invoice.demo_inv.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> loginUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        String token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), principal.getUsername());
        return ResponseEntity.ok(LoginResponse.builder().accessToken(token).build());
    }
}
