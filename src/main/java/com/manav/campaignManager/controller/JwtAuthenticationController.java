package com.manav.campaignManager.controller;

import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.JwtRequest;
import com.manav.campaignManager.entity.JwtResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.jwt.JwtHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationController {

    private UserDetailsService userDetailsService;

    private AuthenticationManager manager;

    private JwtHelper helper;

    @PostMapping("/authenticate")
    public ResponseEntity<GenericResponse<JwtResponse>> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        GenericResponse<JwtResponse> response = GenericResponse.<JwtResponse>builder()
                .status(Status.builder().status(true).message("Operation Successful").error("").build())
                .data(Collections.singletonList(
                        JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build())
                )
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credentials Invalid !!");
        }

    }
}
