package com.manav.campaignManager.controller;


import com.manav.campaignManager.dto.UserDTO;
import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.entity.User;
import com.manav.campaignManager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
    public ResponseEntity<GenericResponse<UserDTO>> findUserById(@PathVariable Integer id){
        User user = serviceObj.findUserById(id);
        UserDTO returnableUser = serviceObj.convertToDTO(user);

        GenericResponse<UserDTO> response =  GenericResponse.<UserDTO>builder()
                .status(Status.builder()
                        .status(true)
                        .message("Operation Successful")
                        .error("")
                        .build()
                )
                .data(returnableUser == null ? Collections.emptyList() : Collections.singletonList(returnableUser))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponse<UserDTO>> addUser(@RequestBody User newUser){
        User updatedUser = serviceObj.addUser(newUser);
        UserDTO returnableUser = serviceObj.convertToDTO(updatedUser);

        GenericResponse<UserDTO> response =  GenericResponse.<UserDTO>builder()
                .status(Status.builder()
                        .status(true)
                        .message("Operation Successful")
                        .error("")
                        .build()
                )
                .data(returnableUser == null ? Collections.emptyList() : Collections.singletonList(returnableUser))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}