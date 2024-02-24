package com.manav.campaignManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Removing this as we need to create Custom User Detail Service in order to use
    // User from data base.
    //    @Bean
    //    public UserDetailsService userDetailsService(){
    //        UserDetails adminUser = User.builder()
    //                .username("admin")
    //                .password(passwordEncoder().encode("admin"))
    //                .roles("ADMIN").build();
    //
    //        UserDetails campaignViewer = User.builder()
    //                .username("user")
    //                .password(passwordEncoder().encode("user"))
    //                .roles("USER").build();
    //
    //        // Creating the users using predefined user class
    //        // Saving the users in InMemoryManager
    //        return new InMemoryUserDetailsManager(adminUser, campaignViewer);
    //    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
