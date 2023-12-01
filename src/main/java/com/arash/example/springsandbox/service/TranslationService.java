package com.arash.example.springsandbox.service;

import com.arash.example.springsandbox.client.TranslationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Slf4j
@RequiredArgsConstructor
@Service
public class TranslationService {

    private final TranslationClient translationClient;

    public String translate(String s) {
        log.info("Translating {}", s);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("translation");
        String translation = translationClient.translate(s);
        stopWatch.stop();
        log.info("Translated {}, time elapsed {}ms", s, stopWatch.getTotalTimeMillis());
        return translation;
    }
}
