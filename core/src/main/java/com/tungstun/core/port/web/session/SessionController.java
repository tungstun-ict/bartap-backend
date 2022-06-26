package com.tungstun.core.port.web.session;

import com.tungstun.core.application.session.SessionCommandHandler;
import com.tungstun.core.application.session.SessionQueryHandler;
import com.tungstun.core.application.session.command.*;
import com.tungstun.core.application.session.query.GetActiveSession;
import com.tungstun.core.application.session.query.GetSession;
import com.tungstun.core.application.session.query.ListSessionsOfBar;
import com.tungstun.core.domain.session.Session;
import com.tungstun.core.port.web.session.request.CreateSessionRequest;
import com.tungstun.core.port.web.session.request.GetActiveBarRequest;
import com.tungstun.core.port.web.session.request.ListSessionsOfBarRequest;
import com.tungstun.core.port.web.session.request.UpdateSessionRequest;
import com.tungstun.core.port.web.session.response.SessionIdResponse;
import com.tungstun.core.port.web.session.response.SessionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars")
public class SessionController {
    private final SessionQueryHandler queryHandler;
    private final SessionCommandHandler commandHandler;

    public SessionController(SessionQueryHandler queryHandler, SessionCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionIdResponse createSession(CreateSessionRequest request) {
        Long sessionId = commandHandler.handle(new CreateSession(request.barId(), request.name()));
        return new SessionIdResponse(sessionId);
    }

    @PutMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public SessionIdResponse updateSession(
            @PathVariable("sessionId") Long sessionId,
            UpdateSessionRequest request) {
        commandHandler.handle(new UpdateSessionName(sessionId, request.name()));
        return new SessionIdResponse(sessionId);
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable("sessionId") Long sessionId) {
        commandHandler.handle(new DeleteSession(sessionId));
    }

    @PatchMapping("/{sessionId}/end")
    @ResponseStatus(HttpStatus.OK)
    public void endSession(@PathVariable("sessionId") Long sessionId) {
        commandHandler.handle(new EndSession(sessionId));
    }

    @PatchMapping("/{sessionId}/lock")
    @ResponseStatus(HttpStatus.OK)
    public void lockSession(@PathVariable("sessionId") Long sessionId) {
        commandHandler.handle(new LockSession(sessionId));
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public SessionResponse getActiveSession(@RequestBody GetActiveBarRequest request) {
        Session session = queryHandler.handle(new GetActiveSession(request.barId()));
        return SessionResponse.of(session);
    }

    @GetMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public SessionResponse getSession(@PathVariable("sessionId") Long sessionId) {
        Session session = queryHandler.handle(new GetSession(sessionId));
        return SessionResponse.of(session);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<SessionResponse> getAllSession(@RequestBody ListSessionsOfBarRequest request) {
        List<Session> sessions = queryHandler.handle(new ListSessionsOfBar(request.barId()));
        return sessions.stream()
                .map(SessionResponse::of)
                .toList();
    }
}
