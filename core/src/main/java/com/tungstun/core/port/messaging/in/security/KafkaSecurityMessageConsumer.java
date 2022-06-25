package com.tungstun.core.port.messaging.in.security;

import com.tungstun.core.domain.user.User;
import com.tungstun.core.domain.user.UserRepository;
import com.tungstun.core.port.messaging.in.security.messages.UserCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        id = "securityMessageListener",
        topics = "security",
        containerFactory = "securityListenerContainerFactory")
public class KafkaSecurityMessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSecurityMessageConsumer.class);

    private final UserRepository userIdRepository;

    public KafkaSecurityMessageConsumer(UserRepository userIdRepository) {
        this.userIdRepository = userIdRepository;
    }

    @KafkaHandler
    public void handleUserCreated(UserCreated o) {
        LOG.info("Received UserCreated: {}", o);
        userIdRepository.save(new User(o.id(), o.username()));
        LOG.info("Handled UserCreated: {}", o);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object o) {
        LOG.warn("Received unknown: {}", o);
    }
}
