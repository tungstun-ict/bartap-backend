package com.tungstun.person.port.messaging.in.user;

import com.tungstun.common.messaging.KafkaMessageConsumer;
import com.tungstun.person.domain.user.User;
import com.tungstun.person.domain.user.UserRepository;
import com.tungstun.person.port.messaging.in.user.message.UserCreated;
import org.springframework.kafka.annotation.KafkaHandler;

public class KafkaUserConsumer extends KafkaMessageConsumer {
    private final UserRepository repository;

    public KafkaUserConsumer(UserRepository repository) {
        this.repository = repository;
    }

    @KafkaHandler
    public void handleUserCreated(UserCreated o) {
        LOG.info("Received UserCreated: {}", o);
        repository.save(new User(o.id(), "", o.username(), ""));
    }
}
