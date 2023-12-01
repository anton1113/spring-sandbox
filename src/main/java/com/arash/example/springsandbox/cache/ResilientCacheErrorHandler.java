package com.arash.example.springsandbox.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class ResilientCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        log.error("Cache get error occurred");
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        log.error("Cache put error occurred");
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        log.error("Cache evict error occurred");
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        log.error("Cache clean error occurred");
    }
}
