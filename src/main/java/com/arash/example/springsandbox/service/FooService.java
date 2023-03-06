package com.arash.example.springsandbox.service;

import com.arash.example.springsandbox.client.FooClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FooService {

    private final FooClient fooClient;

    public String getText() {
        return fooClient.getText();
    }
}
