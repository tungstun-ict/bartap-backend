package com.tungstun.core.port.web.session;

import com.tungstun.core.application.session.SessionCommandHandler;
import com.tungstun.core.application.session.SessionQueryHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bars")
public class SessionController {
    private final SessionQueryHandler queryHandler;
    private final SessionCommandHandler commandHandler;

    public SessionController(SessionQueryHandler queryHandler, SessionCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }
}
