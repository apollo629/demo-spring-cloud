package com.byinal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
class MessageController {

    private final String message;

    public MessageController(@Value("${message}") String message) {
        this.message = message;
    }


    @GetMapping("/message")
    public String getMessage() {
        return this.message;
    }

}
