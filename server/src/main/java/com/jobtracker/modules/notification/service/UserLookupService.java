package com.jobtracker.modules.notification.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jobtracker.common.exceptions.NotFoundException;
import com.jobtracker.modules.users.entity.User;
import com.jobtracker.modules.users.repository.UserRepository;

@Service
public class UserLookupService {
    
    private final UserRepository userRepository;

    public UserLookupService(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }

    public UUID requireUserIdByEmail(String email){
        
       if(email== null)  throw new NotFoundException("No Username found");

       return userRepository.findByEmail(email)
       .orElseThrow(()-> new NotFoundException("User not found: "+ email))
       .getId();
    }

    public String requireUserEmailByUserId(UUID userId){

        return userRepository.findById(userId)
        .orElseThrow(()-> new NotFoundException("User Not found: "+ userId))
        .getEmail();
    }

    

}
