package com.arash.example.springsandbox.client;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class FooClient {

    @Value("${foo.bar.bus}")
    private String text;

    public String getText() {
        return text;
    }
}
