package com.tungstun.person.port.messaging.in.user;

import com.tungstun.common.messaging.KafkaMessageConsumer;
import com.tungstun.person.domain.user.User;
import com.tungstun.person.domain.user.UserRepository;
import com.tungstun.person.port.messaging.in.user.message.UserCreated;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "securityMessageListener", topics = "security")
public class KafkaUserMessageConsumer extends KafkaMessageConsumer {
    private final UserRepository repository;

    public KafkaUserMessageConsumer(UserRepository repository) {
        this.repository = repository;
    }

    @KafkaHandler
    public void handleUserCreated(UserCreated o) {
        LOG.info("Received UserCreated: {}", o);
        repository.save(new User(o.id(), "", o.username(), ""));
    }
}
