package com.jobtracker.modules.auth.service;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobtracker.common.exceptions.ConflictException;
import com.jobtracker.common.exceptions.UnauthorisedException;
import com.jobtracker.modules.auth.dto.AuthResponse;
import com.jobtracker.modules.auth.dto.LoginRequest;
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


        String email=request.getEmail().trim().toLowerCase();
        
        if(userRepository.existsByEmail(email)){
            throw new ConflictException("Email is already in use");
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

    public AuthResponse login(LoginRequest request){
        
        String email=request.getEmail().trim().toLowerCase();
        User user=userRepository.findByEmail(email).orElseThrow(
            ()-> new UnauthorisedException("Invalid email or password"));

        boolean passwordMatches=passwordEncoder.matches(request.getPassword(), user.getPasswordHash());

        if(!passwordMatches){
            throw new UnauthorisedException("Invalid email or password");
        }

        return new AuthResponse(
            "ACCESS_TOKEN_PLACEHOLDER",
            "REFRESH_TOKEN_PLACEHOLDER"
        );
    }
}
