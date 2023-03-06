package com.arash.example.springsandbox.controllers;

import com.arash.example.springsandbox.service.FooService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping({"", "/v1"})
@RestController
public class FooV1Controller {

    private final FooService fooService;

    @GetMapping(value = "/text")
    public String getText() {
        return fooService.getText();
    }
}
