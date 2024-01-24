package com.sample2.redis.service;

import com.sample2.redis.model.Req;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(Req req) {
        redisTemplate.convertAndSend("ch1", req);

    }
}
