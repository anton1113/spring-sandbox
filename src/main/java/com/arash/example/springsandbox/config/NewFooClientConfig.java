package com.arash.example.springsandbox.config;

import com.arash.example.springsandbox.client.FooClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewFooClientConfig {

    @Bean("newFooClient")
    FooClient fooClient() {
        return new FooClient("bar");
    }
}
