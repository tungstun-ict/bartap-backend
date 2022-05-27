package com.tungstun.security.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.Valid;

public class UserService implements UserDetailsService {


    public void registerUser(@Valid RegisterUser command) {
        String encodedPassword = passwordEncoder.encode(command.password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
