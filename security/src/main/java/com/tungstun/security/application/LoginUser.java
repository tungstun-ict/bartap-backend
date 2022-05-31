package com.tungstun.security.application;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginUser {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 50, message = "Password must be between {min} and {max} characters long")
    private String password;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
