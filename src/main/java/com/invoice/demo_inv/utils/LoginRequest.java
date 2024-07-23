package com.invoice.demo_inv.utils;

import lombok.Builder;
import lombok.Getter;

// login request type
@Getter
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
