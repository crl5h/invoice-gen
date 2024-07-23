package com.invoice.demo_inv.security;

import com.invoice.demo_inv.entity.User;
import com.invoice.demo_inv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);

        // building the userPrincipal from db
        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .first_name(user.getFirstName())
                .last_name(user.getLastName())
                .password(user.getPassword())
                .build();
    }
}
