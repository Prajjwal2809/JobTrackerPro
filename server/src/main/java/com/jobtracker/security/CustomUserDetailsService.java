package com.jobtracker.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.jobtracker.modules.users.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
   

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
       
        return new UserPrincipal(
            user.getId(),
            user.getEmail(),
            user.getPasswordHash(), 
            List.of(
                new SimpleGrantedAuthority("ROLE_USER")
            ));
    }
    
}