package com.manav.campaignManager.service;

import com.manav.campaignManager.exceptionHandler.exceptions.*;
import com.manav.campaignManager.repository.UserCrud;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.manav.campaignManager.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserCrud userJPA;
    
    UserService(final UserCrud userJPA ){
        this.userJPA = userJPA;
    }
    
    public List<User> getAllUsers() {
        return (List<User>) userJPA.findAll();
    }

    private Boolean validateUserPayloadForAdd( User user ){
        // If user id is present in the request body
        if( user.getUserId() != null && user.getUserId() != 0)
            throw new InvalidUserRequest("User Id should be zero for new email id");

        if( user.getFirstName() == null || user.getFirstName().isEmpty())
            throw new InvalidUserRequest("First name cannot be empty");

        if( user.getEmailId() == null || user.getEmailId().isEmpty() )
            throw new InvalidUserRequest("Email Id cannot be empty");

        if( user.getPassword() == null || user.getPassword().isEmpty())
            throw new InvalidUserRequest("Empty password");

        // CHECK IF USER ALREADY EXISTS OR NOT
        Optional<User> existingUser = userJPA.findByEmailId( user.getEmailId() );
        if( existingUser.isPresent() ) {
            throw new UserAlreadyExists("Email Id is already registered to another user");
        }

        if( user.getRegistrationTime() == null || user.getRegistrationTime().isEmpty())
            user.setRegistrationTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return true;
    }

    @Transactional
    public User addUser(User newUser) {
        if(!validateUserPayloadForAdd(newUser))
            return null;
        User updatedUser = userJPA.save(newUser);
        return updatedUser;
    }
    
    public User findUserById(Integer user_id){
        if( user_id == null || user_id < 0)
            throw new InvalidUserRequest("Please enter a valid user id");
        
        return userJPA.findById(user_id).orElse(null);
    }
    
    public User findByEmail(String emailId) {
        if( emailId == null || emailId.isEmpty())
            throw new InvalidUserRequest("Please enter a valid Email Id");
        
        return userJPA.findByEmailId( emailId ).orElse(null);
    }
}
