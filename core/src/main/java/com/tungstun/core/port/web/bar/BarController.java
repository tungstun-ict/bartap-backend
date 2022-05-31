package com.tungstun.core.port.web.bar;

import com.tungstun.core.application.bar.BarCommandHandler;
import com.tungstun.core.application.bar.BarQueryHandler;
import com.tungstun.core.application.bar.command.CreateBar;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bars")
public class BarController {
    private final BarQueryHandler queryHandler;
    private final BarCommandHandler commandHandler;

    public BarController(BarQueryHandler queryHandler, BarCommandHandler commandHandler) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }
//    @PostMapping
//    public ResponseEntity<> createBar() {
//        Bar bar = commandHandler.handle(new CreateBar())
//    }
}
