package com.manav.campaignManager.controller;


import com.manav.campaignManager.entities.GenericResponse;
import com.manav.campaignManager.entities.Status;
import com.manav.campaignManager.entities.User;
import com.manav.campaignManager.exceptionHandlers.exceptions.InvalidUserRequest;
import com.manav.campaignManager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    final UserService serviceObj;

    UserController(final UserService userService){
        this.serviceObj = userService;
    }

//    @GetMapping({"", "/"})
//    public ResponseEntity<GenericResponse<User>> getAllUsers(
//            @RequestParam(required = false) String emailId,
//            @RequestParam(required = false) Integer userId
//    ){
//        List<User> responseList;
//
//        if(emailId != null && userId != null )
//            throw new InvalidUserRequest("Please enter either user id or email id");
//        else if( emailId != null )
//            responseList = serviceObj.findByEmail(emailId);
//        else if( userId != null )
//            responseList = serviceObj.findUserById(userId);
//        else
//            responseList = serviceObj.getAllUsers();
//
//        GenericResponse<User> response = GenericResponse.<User>builder()
//                .status(Status.builder()
//                        .status(true)
//                        .message("Operation Successful")
//                        .error("")
//                        .build()
//                )
//                .data(responseList)
//                .build();
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<User>> findUserById(@PathVariable Integer id){
        User user = serviceObj.findUserById(id);

        GenericResponse<User> response =  GenericResponse.<User>builder()
                .status(Status.builder()
                        .status(true)
                        .message("Operation Successful")
                        .error("")
                        .build()
                )
                .data(user == null ? Collections.emptyList() : Collections.singletonList(user))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<User>> addUser(@RequestBody User newUser){
        User updatedUser = serviceObj.addUser(newUser);
        GenericResponse<User> response =  GenericResponse.<User>builder()
                .status(Status.builder()
                        .status(true)
                        .message("Operation Successful")
                        .error("")
                        .build()
                )
                .data(updatedUser == null ? Collections.emptyList() : Collections.singletonList(updatedUser))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}