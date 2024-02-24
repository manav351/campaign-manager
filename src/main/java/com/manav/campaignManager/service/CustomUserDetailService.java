package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.User;
import com.manav.campaignManager.exceptionHandler.exceptions.UserDoestNotExist;
import com.manav.campaignManager.repository.UserCrud;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    UserCrud userCrud;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from database
        return userCrud.findByEmailId( username ).orElseThrow(() -> new UserDoestNotExist("No user details found while checking for user user authentication") );
    }
}
