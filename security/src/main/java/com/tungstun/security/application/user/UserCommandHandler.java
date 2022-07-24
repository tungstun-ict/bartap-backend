package com.tungstun.security.application.user;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tungstun.common.messaging.KafkaMessageProducer;
import com.tungstun.common.security.jwt.JwtValidator;
import com.tungstun.security.application.user.command.*;
import com.tungstun.security.application.user.event.UserCreated;
import com.tungstun.security.application.user.event.UserUpdated;
import com.tungstun.security.domain.jwt.JwtTokenGenerator;
import com.tungstun.security.domain.user.User;
import com.tungstun.security.domain.user.UserRepository;
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
public class UserCommandHandler {
    private final UserQueryHandler queryHandler;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtValidator jwtValidator;
    private final KafkaMessageProducer producer;

    public UserCommandHandler(UserQueryHandler queryHandler, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenGenerator jwtTokenGenerator, JwtValidator jwtValidator, KafkaMessageProducer producer) {
        this.queryHandler = queryHandler;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtValidator = jwtValidator;
        this.producer = producer;
    }

    public void handle(@Valid RegisterUser command) {
        String encodedPassword = passwordEncoder.encode(command.password());
        User user = userRepository.save(new User(
                command.username(),
                encodedPassword,
                command.mail().strip(),
                command.firstName(),
                command.lastName(),
                command.phoneNumber(), new ArrayList<>()
        ));

        producer.publish(user.getId(), new UserCreated(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getMail(),
                user.getPhoneNumber().getValue()
        ));
    }

    public void handle(@Valid UpdateUser command) {
        User user = (User) queryHandler.loadUserByUsername(command.username());
        if (command.firstName() != null) user.setFirstName(command.firstName());
        if (command.lastName() != null) user.setLastName(command.lastName());
        if (command.phoneNumber() != null) user.setPhoneNumber(command.phoneNumber());
        userRepository.save(user);

        producer.publish(user.getId(), new UserUpdated(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber().getValue()
        ));
    }

    public Map<String, String> handle(@Valid LoginUser command) throws LoginException {
        User user = (User) queryHandler.loadUserByUsername(command.username());
        user.canAuthenticate();
        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new LoginException("Incorrect password");
        }
        return Map.of(
                "token_type", "bearer",
                "access_token", jwtTokenGenerator.createAccessToken(user),
                "refresh_token", jwtTokenGenerator.createRefreshToken()
        );
    }

    public Map<String, String> handle(@Valid RefreshAccessToken command) {
        jwtValidator.verifyRefreshToken(command.refreshToken());
        DecodedJWT accessTokenInfo = jwtValidator.verifyAccessToken(command.accessToken());
        User userDetails = (User) queryHandler.loadUserByUsername(accessTokenInfo.getSubject());
        String newAccessToken = jwtTokenGenerator.createAccessToken(userDetails);
        return Collections.singletonMap("access_token", newAccessToken);
    }

    public void handle(@Valid VerifyUser command) {
        jwtValidator.verifyAccessToken(command.accessToken());
    }
}
