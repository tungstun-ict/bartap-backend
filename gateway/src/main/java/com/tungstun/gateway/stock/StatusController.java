package com.tungstun.gateway.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StatusController {

    @GetMapping("/")
    public Mono<String> status() {
        // `getTemplate` - fetch html template from your storage in a reactive way, should return `Mono<String>`
//        return getTemplate(pageId)
//                .flatMap(htmlTemplate -> {
//                    // apply request parameters to your html page, like replacing placeholders with links, user name etc.
//                });
//        return "Hello World!";
        return Mono.justOrEmpty("Hello World!");
    }

}
