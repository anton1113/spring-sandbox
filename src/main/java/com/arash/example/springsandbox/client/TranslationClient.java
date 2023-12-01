package com.arash.example.springsandbox.client;

import constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TranslationClient {

    @Cacheable(value = CacheConstants.TRANSLATIONS_CACHE_NAME, keyGenerator = CacheConstants.TRANSLATIONS_KEY_GENERATOR)
    public String translate(String s) {
        log.info("Sending translation request for {}", s);
        return "Translated " + s;
    }
}
