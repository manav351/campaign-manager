package com.manav.campaignManager.service;

import com.manav.campaignManager.dto.UserDTO;
import com.manav.campaignManager.exceptionHandler.exceptions.*;
import com.manav.campaignManager.repository.UserCrud;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.manav.campaignManager.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    
    private final UserCrud userJPA;
    private final PasswordEncoder passwordEncoder;
    
    public List<User> getAllUsers() {
        return (List<User>) userJPA.findAll();
    }

    private void validateUserPayloadForAdd( User user ){
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
    }

    @Transactional
    public User addUser(User newUser) {
        validateUserPayloadForAdd(newUser);

        if( newUser.getRegistrationTime() == null || newUser.getRegistrationTime().isEmpty())
            newUser.setRegistrationTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        if( newUser.getUserRole() == null || newUser.getUserRole().isEmpty() )
            newUser.setUserRole("viewer");

        newUser.setPassword( passwordEncoder.encode( newUser.getPassword() ) );

        return userJPA.save(newUser);
    }
    
    public User findUserById(Integer user_id){
        if( user_id == null || user_id < 0)
            throw new InvalidUserRequest("Please enter a valid user id");
        
        return userJPA.findById(user_id).orElse(null);
    }
    
    public User findUserByEmailId(String emailId) {
        if( emailId == null || emailId.isEmpty())
            throw new InvalidUserRequest("Please enter a valid Email Id");
        
        return userJPA.findByEmailId( emailId ).orElse(null);
    }

    public UserDTO convertToDTO(User user) {
        if( user == null )
            return null;
        return UserDTO.builder()
                .userId( user.getUserId() )
                .firstName( user.getFirstName() )
                .lastName( user.getLastName() )
                .emailId( user.getEmailId() )
                .userRole( user.getUserRole() )
                .build();
    }
}
