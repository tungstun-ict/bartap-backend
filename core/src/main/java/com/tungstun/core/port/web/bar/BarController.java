package com.tungstun.core.port.web.bar;

import com.tungstun.common.security.BartapUserDetails;
import com.tungstun.common.web.IdResponse;
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
import com.tungstun.core.port.web.bar.response.BarResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Create a new bar",
            description = "A new bar is created with the information provided in the request body",
            tags = "Bar"
    )
    public IdResponse createBar(@RequestBody CreateBarRequest request) {
        Long id = commandHandler.handle(new CreateBar(request.name(), request.address(), request.mail(), request.phoneNumber()));
        return new IdResponse(id);
    }

    @PutMapping("/{barId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update bar",
            description = "The bar with the given id id is updated with the information provided in the request body",
            tags = "Bar"
    )
    public IdResponse updateBar(@PathVariable("barId") Long id, @RequestBody UpdateBarRequest request) {
        commandHandler.handle(new UpdateBar(id, request.name(), request.address(), request.mail(), request.phoneNumber()));
        return new IdResponse(id);
    }

    @DeleteMapping("/{barId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete bar",
            description = "The bar with given id is deleted",
            tags = "Bar"
    )
    public void deleteBar(@PathVariable("barId") Long id) {
        commandHandler.handle(new DeleteBar(id));
    }

    private BarResponse mapToResponse(Bar bar) {
        return new BarResponse(bar.getId(), bar.getName(), bar.getAddress(), bar.getMail(), bar.getPhoneNumber());
    }

    @GetMapping("/{barId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get bar",
            description = "The bar with given id is queried",
            tags = "Bar"
    )
    public BarResponse getBar(@PathVariable("barId") Long id) {
        Bar bar = queryHandler.handle(new GetBar(id));
        return mapToResponse(bar);
    }

    @GetMapping("/owned")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all bars owned by user",
            description = "All bars owned by a user are queried based on the user's authentication",
            tags = "Bar"
    )
    public List<BarResponse> getOwnedBar(Authentication authentication) {
        BartapUserDetails userDetails = (BartapUserDetails) authentication.getPrincipal();
        List<Long> ownedBarIds = userDetails.authorizations().parallelStream()
                .filter(auth -> auth.role().equalsIgnoreCase("OWNER"))
                .map(auth -> Long.parseLong(auth.barId()))
                .toList();
        return queryHandler.handle(new GetOwnedBars(ownedBarIds)).stream()
                .map(this::mapToResponse)
                .toList();
    }
}
