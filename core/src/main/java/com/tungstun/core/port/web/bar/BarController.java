package com.tungstun.core.port.web.bar;

import com.tungstun.core.application.bar.BarCommandHandler;
import com.tungstun.core.application.bar.BarQueryHandler;
import com.tungstun.core.application.bar.command.CreateBar;
import com.tungstun.core.application.bar.command.DeleteBar;
import com.tungstun.core.application.bar.command.UpdateBar;
import com.tungstun.core.application.bar.query.GetBar;
import com.tungstun.core.application.bar.query.GetOwnedBars;
import com.tungstun.core.domain.bar.Bar;
import com.tungstun.core.port.web.bar.request.CreateBarRequest;
import com.tungstun.core.port.web.bar.request.UpdateBarRequest;
import com.tungstun.core.port.web.bar.response.BarIdResponse;
import com.tungstun.core.port.web.bar.response.BarResponse;
import com.tungstun.sharedlibrary.security.BartapUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars")
public class BarController {
    private final BarQueryHandler queryHandler;
    private final BarCommandHandler commandHandler;

    public BarController(BarQueryHandler queryHandler, BarCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BarIdResponse createBar(CreateBarRequest request) {
        Long id = commandHandler.handle(new CreateBar(request.name(), request.address(), request.mail(), request.phoneNumber()));
        return new BarIdResponse(id);
    }

    @PutMapping("/{barId}")
    @ResponseStatus(HttpStatus.OK)
    public BarIdResponse updateBar(@PathVariable("barId") Long id, UpdateBarRequest request) {
        commandHandler.handle(new UpdateBar(id, request.name(), request.address(), request.mail(), request.phoneNumber()));
        return new BarIdResponse(id);
    }

    @DeleteMapping("/{barId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBar(@PathVariable("barId") Long id) {
        commandHandler.handle(new DeleteBar(id));
    }

    private BarResponse mapToResponse(Bar bar) {
        return new BarResponse(bar.getId(), bar.getName(), bar.getAddress(), bar.getMail(), bar.getPhoneNumber());
    }

    @GetMapping("/{barId}")
    @ResponseStatus(HttpStatus.OK)
    public BarResponse getBar(@PathVariable("barId") Long id) {
        Bar bar = queryHandler.handle(new GetBar(id));
        return mapToResponse(bar);
    }

    @GetMapping("/owned")
    @ResponseStatus(HttpStatus.OK)
    public List<BarResponse> getOwnedBar(Authentication authentication) {
        BartapUserDetails userDetails = (BartapUserDetails) authentication.getPrincipal();
        return queryHandler.handle(new GetOwnedBars(userDetails.getId()))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
