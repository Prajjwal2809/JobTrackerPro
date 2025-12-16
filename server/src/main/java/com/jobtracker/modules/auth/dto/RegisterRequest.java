package com.jobtracker.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {

    @NotBlank(message="Name is required")
    @Size(min=2, max=120, message="Name should be in between 2 to 120 characters")
    private String name;


    @NotBlank(message="Email is required")
    @Email(message="Enter a valid email")
    @Size(max=180, message="Email should be at most 180 characters")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=8, max=72, message=" Password should be in between 8 to 72 characters")
    private String password;
    
}
