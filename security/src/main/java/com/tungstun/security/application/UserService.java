package com.tungstun.security.application;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tungstun.security.domain.jwt.JwtTokenGenerator;
import com.tungstun.security.domain.user.User;
import com.tungstun.security.domain.user.UserRepository;
import com.tungstun.sharedlibrary.exception.UserNotFoundException;
import com.tungstun.sharedlibrary.security.jwt.JwtValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@Controller
@Validated
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtValidator jwtValidator;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenGenerator jwtTokenGenerator, JwtValidator jwtValidator) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtValidator = jwtValidator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username '%s' was not found", username)));
    }

    public void registerUser(@Valid RegisterUser command) {
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        userRepository.save(new User(
                command.getUsername(),
                encodedPassword,
                command.getMail().strip(),
                command.getFirstName(),
                command.getLastName(),
                new ArrayList<>()
        ));
    }

    public Map<String, String> loginUser(LoginUser command) throws LoginException {
        User user = (User) loadUserByUsername(command.username());
        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new LoginException("Incorrect password");
        }

        return Map.of("token_type", "bearer",
                "access_token", jwtTokenGenerator.createAccessToken(user),
                "refresh_token", jwtTokenGenerator.createRefreshToken());
    }

    public Map<String, String> refreshUser(String accessToken, String refreshToken) {
        jwtValidator.verifyToken(refreshToken);
        DecodedJWT jwtInfo = jwtValidator.verifyToken(accessToken);
        User bartapUserDetails = (User) loadUserByUsername(jwtInfo.getSubject());
        String newAccessToken = jwtTokenGenerator.createAccessToken(bartapUserDetails);
        return Map.of("access_token", newAccessToken);
    }

    public void verifyUser(String accessToken, String tokenType) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new JWTVerificationException("No access token present");
        }
        if (!tokenType.equals("bearer")) {
            throw new JWTVerificationException("Wrong token type");
        }
        jwtValidator.verifyToken(accessToken);
    }
}
