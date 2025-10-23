package com.thanglong.project.usecase.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,String> redisTemplate;

    public void setValue(String key, String value, long expirationTimeInSeconds) {
        redisTemplate.opsForValue().set(key, value, expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
    public void addBlacklistToken(String token, long expirationTimeInSeconds) {
        setValue("blacklist:" + token, "blacklisted", expirationTimeInSeconds);
    }

    public boolean isBlackListed(String token) {

        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token));
    }
    public  void addToken(String token, long expirationTimeInSeconds) {
        setValue("freshtoken:" + token, "active", expirationTimeInSeconds);
    }
}

