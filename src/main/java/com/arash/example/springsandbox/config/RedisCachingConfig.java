package com.arash.example.springsandbox.config;

import com.arash.example.springsandbox.cache.ResilientCacheErrorHandler;
import constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Arrays;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Configuration
public class RedisCachingConfig extends CachingConfigurerSupport {

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.parse("P1D"))
                .disableCachingNullValues()
                .disableKeyPrefix()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public KeyGenerator translationsKeyGenerator() {
        return (target, method, params) -> {
            StringJoiner key = new StringJoiner(":");
            key.add(serviceName);
            key.add(CacheConstants.TRANSLATIONS_CACHE_NAME);
            Arrays.stream(params).map(Object::toString).forEach(key::add); // todo avoid stream, use foreach
            return key.toString();
        };
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(RedisCacheConfiguration cacheConfiguration) {
        return builder ->
                builder.withCacheConfiguration(
                        CacheConstants.TRANSLATIONS_CACHE_NAME, cacheConfiguration
                );
    }

//    @Override
//    public CacheErrorHandler errorHandler() {
//        return new ResilientCacheErrorHandler();
//    }
}
