package com.jobtracker.modules.auth.service;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobtracker.modules.auth.dto.AuthResponse;
import com.jobtracker.modules.auth.dto.RegisterRequest;
import com.jobtracker.modules.users.entity.User;
import com.jobtracker.modules.users.repository.UserRepository;


@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request){

        System.out.println("Registering user with email: " + request);

        String email=request.getEmail().trim().toLowerCase();
        
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already in use");
        }

        String name=request.getName().trim();
        String passwordHash=passwordEncoder.encode(request.getPassword());
        OffsetDateTime now=OffsetDateTime.now();

        User newUser=new User(
            name,
            email,
            passwordHash,
            now,
            now
        );

        userRepository.save(newUser);

        return new AuthResponse(
                "ACCESS_TOKEN_PLACEHOLDER",
                "REFRESH_TOKEN_PLACEHOLDER"
        );
        
    }
}
