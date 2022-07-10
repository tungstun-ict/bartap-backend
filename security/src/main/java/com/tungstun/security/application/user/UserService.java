package com.tungstun.security.application.user;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tungstun.common.exception.UserNotFoundException;
import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.common.security.exception.CannotAuthenticateException;
import com.tungstun.common.security.jwt.JwtValidator;
import com.tungstun.security.application.user.command.LoginUser;
import com.tungstun.security.application.user.command.RefreshAccessToken;
import com.tungstun.security.application.user.command.RegisterUser;
import com.tungstun.security.application.user.command.VerifyUser;
import com.tungstun.security.domain.jwt.JwtTokenGenerator;
import com.tungstun.security.domain.user.User;
import com.tungstun.security.domain.user.UserRepository;
import com.tungstun.security.port.messaging.out.message.UserCreated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.security.auth.login.LoginException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Service
@Validated
@Transactional
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtValidator jwtValidator;
    private final KafkaMessageProducer producer;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenGenerator jwtTokenGenerator, JwtValidator jwtValidator, KafkaMessageProducer producer) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtValidator = jwtValidator;
        this.producer = producer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username '%s' was not found", username)));
    }

    public void handle(@Valid RegisterUser command) {
        String encodedPassword = passwordEncoder.encode(command.password());
        User user = userRepository.save(new User(
                command.username(),
                encodedPassword,
                command.mail().strip(),
                command.firstName(),
                command.lastName(),
                new ArrayList<>()
        ));

        producer.publish(user.getId(), new UserCreated(user.getId(), user.getUsername()));
    }

    private void validateUserCanAuthenticate(User user) {
        if (user.isAccountNonExpired())
            throw new CannotAuthenticateException("Account expired. An expired account cannot be authenticated.");
        if (user.isAccountNonLocked())
            throw new CannotAuthenticateException("Account locked. A locked account cannot be authenticated.");
        if (user.isCredentialsNonExpired())
            throw new CannotAuthenticateException("Account credentials expired. Expired credentials prevent authentication.");
        if (user.isEnabled())
            throw new CannotAuthenticateException("Account disabled. A disabled account cannot be authenticated.");
    }

    public Map<String, String> handle(@Valid LoginUser command) throws LoginException {
        User user = (User) loadUserByUsername(command.username());
        validateUserCanAuthenticate(user);
        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new LoginException("Incorrect password");
        }
        return Map.of("token_type", "bearer",
                "access_token", jwtTokenGenerator.createAccessToken(user),
                "refresh_token", jwtTokenGenerator.createRefreshToken());
    }

    public Map<String, String> handle(@Valid RefreshAccessToken command) {
        jwtValidator.verifyRefreshToken(command.refreshToken());
        DecodedJWT accessTokenInfo = jwtValidator.verifyAccessToken(command.accessToken());
        User userDetails = (User) loadUserByUsername(accessTokenInfo.getSubject());
        String newAccessToken = jwtTokenGenerator.createAccessToken(userDetails);
        return Collections.singletonMap("access_token", newAccessToken);
    }

    public void handle(@Valid VerifyUser command) {
        if (command.accessToken() == null || command.accessToken().isEmpty()) {
            throw new JWTVerificationException("No access token present");
        }
        if (!command.tokenType().equals("bearer")) {
            throw new JWTVerificationException("Wrong token type");
        }
        jwtValidator.verifyAccessToken(command.accessToken());
    }
}
