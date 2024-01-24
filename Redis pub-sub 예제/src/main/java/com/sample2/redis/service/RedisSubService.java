package com.sample2.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample2.redis.model.Req;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisSubService implements MessageListener {
    public static List<String> messageList = new ArrayList<>();
    // JSON 문자열을 Java 객체로 변환하는데 사용.
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            Req req = mapper.readValue(message.getBody(), Req.class);
            messageList.add(message.toString());

            System.out.println("받은 메시지 = " + message.toString());
            System.out.println("FileKey = " + req.getFileKey());
            System.out.println("FilterInfo = " + req.getFilterInfo());
            System.out.println("UserId = " + req.getUserId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
