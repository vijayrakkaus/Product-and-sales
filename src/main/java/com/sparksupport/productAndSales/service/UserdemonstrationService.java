package com.sparksupport.productAndSales.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserdemonstrationService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")) {
            return User.builder()
                .username("admin")
                .password("password") 
                .roles("ADMIN")
                .build();
        } else {
            return User.builder()
                .username(username)
                .password("password") 
                .roles()
                .build();
        }
    }
}
