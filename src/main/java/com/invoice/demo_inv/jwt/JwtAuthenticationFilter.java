package com.invoice.demo_inv.jwt;

import com.invoice.demo_inv.security.UserPrincipalAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode) // .map(decodedJWT -> jwtDecoder.decode(decodedJWT))
                .map(jwtToPrincipalConverter::convertToPrincipal)
                .map(UserPrincipalAuthenticationToken::new) // we are passing the UserPrincipal from the above line in
                                                            // UserPrincipalAuthenticationToken constructor.
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response); // gets invoked for each incoming HTTP request

    }

    /**
     * Extracts an authentication token from an incoming HTTP request's
     * "Authorization" header.
     * 
     * @param request The HttpServletRequest representing the incoming HTTP request.
     * @return An Optional containing the extracted authentication token if found,
     *         or an empty Optional if not present.
     */
    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            return Optional.empty();
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }

        return Optional.empty();
    }

}
