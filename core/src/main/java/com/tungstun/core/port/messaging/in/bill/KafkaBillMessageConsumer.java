package com.tungstun.core.port.messaging.in.bill;

import com.tungstun.core.application.session.SessionCommandHandler;
import com.tungstun.core.application.session.command.AddBill;
import com.tungstun.core.application.session.command.RemoveBill;
import com.tungstun.core.port.messaging.in.bill.message.BillCreated;
import com.tungstun.core.port.messaging.in.bill.message.BillDeleted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "billMessageListener", topics = "bill")
public class KafkaBillMessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaBillMessageConsumer.class);
    private final SessionCommandHandler commandHandler;

    public KafkaBillMessageConsumer(SessionCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @KafkaHandler
    public void handleBillCreated(BillCreated o) {
        LOG.info("Received BillCreated: {}", o);
        commandHandler.handle(new AddBill(o.sessionId(), o.id()));
    }

    @KafkaHandler
    public void handleBillCreated(BillDeleted o) {
        LOG.info("Received BillDeleted: {}", o);
        commandHandler.handle(new RemoveBill(o.sessionId(), o.id()));
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object o) {
        LOG.warn("Received unknown: {}", o);
    }
}
