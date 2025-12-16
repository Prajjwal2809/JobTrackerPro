package com.jobtracker.modules.auth.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private final String accessToken;
    private final String refreshToken;

    
}
