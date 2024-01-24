package com.sample2.redis.controller;

import com.sample2.redis.model.Req;
import com.sample2.redis.service.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisPubService redisPubService;

    @PostMapping("api/req")
    public String pubSub(@RequestBody Req req) {
        //메시지 보내기
        redisPubService.sendMessage(req);

        return "success";
    }
}
