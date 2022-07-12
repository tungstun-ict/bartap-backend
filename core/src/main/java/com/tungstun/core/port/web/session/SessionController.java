package com.tungstun.core.port.web.session;

import com.tungstun.core.application.session.SessionCommandHandler;
import com.tungstun.core.application.session.SessionQueryHandler;
import com.tungstun.core.application.session.command.*;
import com.tungstun.core.application.session.query.GetActiveSession;
import com.tungstun.core.application.session.query.GetSession;
import com.tungstun.core.application.session.query.ListSessionsOfBar;
import com.tungstun.core.domain.session.Session;
import com.tungstun.core.port.web.session.request.*;
import com.tungstun.core.port.web.session.response.SessionIdResponse;
import com.tungstun.core.port.web.session.response.SessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars/{barId}/sessions")
public class SessionController {
    private final SessionQueryHandler queryHandler;
    private final SessionCommandHandler commandHandler;

    public SessionController(SessionQueryHandler queryHandler, SessionCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new session",
            description = "A new session is created for the bar with the given bar id and the name provided in the request body",
            tags = "Session"
    )
    public SessionIdResponse createSession(@PathVariable("barId") Long barId,
                                           @RequestBody CreateSessionRequest request) {
        Long sessionId = commandHandler.handle(new CreateSession(barId, request.name()));
        return new SessionIdResponse(sessionId);
    }

    @PutMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update session",
            description = "The session with the given id of bar with given id is updated with the information provided in the request body",
            tags = "Session"
    )
    public SessionIdResponse updateSession(
            @PathVariable("barId") Long barId,
            @PathVariable("sessionId") Long sessionId,
            @RequestBody UpdateSessionRequest request) {
        commandHandler.handle(new UpdateSessionName(sessionId, barId, request.name()));
        return new SessionIdResponse(sessionId);
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete session",
            description = "The session with given id is deleted",
            tags = "Session"
    )
    public void deleteSession(
            @PathVariable("barId") Long barId,
            @PathVariable("sessionId") Long sessionId) {
        commandHandler.handle(new DeleteSession(sessionId, barId));
    }

    @PatchMapping("/{sessionId}/end")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "End session",
            description = "The session with the given id is ended",
            tags = "Session"
    )
    public void endSession(
            @PathVariable("barId") Long barId,
            @PathVariable("sessionId") Long sessionId) {
        commandHandler.handle(new EndSession(sessionId, barId));
    }

    @PatchMapping("/{sessionId}/lock")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Lock session",
            description = "The session with the given id is locked",
            tags = "Session"
    )
    public void lockSession(
            @PathVariable("barId") Long barId,
            @PathVariable("sessionId") Long sessionId) {
        commandHandler.handle(new LockSession(sessionId, barId));
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get active session",
            description = "The currently active session of the bar with the given bar id is queried",
            tags = "Session"
    )
    public SessionResponse getActiveSession(@PathVariable("barId") Long barId) {
        Session session = queryHandler.handle(new GetActiveSession(barId));
        return SessionResponse.of(session);
    }

    @GetMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get session",
            description = "The session with the given id of the bar with the given bar id is queried",
            tags = "Session"
    )
    public SessionResponse getSession(
            @PathVariable("barId") Long barId,
            @PathVariable("sessionId") Long sessionId) {
        Session session = queryHandler.handle(new GetSession(sessionId, barId));
        return SessionResponse.of(session);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all sessions",
            description = "All session of the bar with the given bar id are queried",
            tags = "Session"
    )
    public List<SessionResponse> getAllSession(@RequestBody ListSessionsOfBarRequest request) {
        List<Session> sessions = queryHandler.handle(new ListSessionsOfBar(request.barId()));
        return sessions.stream()
                .map(SessionResponse::of)
                .toList();
    }
}
