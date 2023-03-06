package com.arash.example.springsandbox.controllers;

import com.arash.example.springsandbox.config.NewFooClientConfig;
import com.arash.example.springsandbox.service.FooService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Import(NewFooClientConfig.class)
@RequiredArgsConstructor
@RequestMapping({"", "/v2"})
@RestController
public class FooV2Controller {

    private final FooService fooService;
    private final ApplicationContext applicationContext;

    @GetMapping(value = "/text")
    public String getText() {
        return fooService.getText();
    }
}
