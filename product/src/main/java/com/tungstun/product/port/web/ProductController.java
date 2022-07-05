package com.tungstun.product.port.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bills")
public class ProductController {

    @GetMapping
    public String getHello() {
        return "Hello World";
    }
}
