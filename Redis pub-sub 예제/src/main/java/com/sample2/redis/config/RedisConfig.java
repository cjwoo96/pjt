package com.sample2.redis.config;

import com.sample2.redis.model.Req;
import com.sample2.redis.service.RedisSubService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /*
     RedisConnectionFactory : Redis와의 연결을 담당.
     LettuceConnectionFactory :  Redis 클라이언트 라이브러리인 Lettuce를 사용하여 Redis 서버에 연결하기 위한 팩토리
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
    
    //redisTemplate 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        /*
        Redis 데이터 액세스를 추상화하는 템플릿입니다.
        이 템플릿을 사용하여 Redis에서 데이터를 저장하고, 조회하고, 삭제하는 등의 작업을 수행할 수 있습니다.
        Redis는 NoSQL 형태의 데이터베이스로 여기서는 키는 String 형태, 값은 Object 형태
         */
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // Redis 서버에 연결할 때 사용할 ConnectionFactory 설정.
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        /*
         Redis에서 키는 문자열로 저장됨.
         StringRedisSerializer는 Java의 String타입을 Redis 문자열 데이터로 변환하는 직렬화 방식.
         serializer는 Java의 String 객체를 Redis가 이해 할 수 있는 문자열 데이터로 변환함.
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        /*
        Redis에 값을 저장 할 때 사용할 직렬화 방식을 설정함.
        ChatMessage 클래스의 인스턴스를 JSON 형식으로 직렬화하는 데 사용됨.

        또, Redis에서 데이터를 조회 할 때, 이 직렬화기는 저장된 JSON 문자열을 "ChatMessage" 객체로 다시 역직렬화 함

         */
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Req.class));
        return redisTemplate;
    }
    
    /*
    리스너어댑터 설정

    메시지를 수신 할 때 호출된 메서드 정의

    new RedisSubService()
    -> Redis에서 메시지를 수신 할 때 실행될 로직.
     */
    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisSubService());
    }
    
    /*
    컨테이너 설정

     */
    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), ch1());
        container.addMessageListener(messageListenerAdapter(), ch2());
        return container;
    }

    //pub/sub 토픽 설정
    @Bean
    ChannelTopic ch1() {
        return new ChannelTopic("ch1");
    }

    @Bean
    ChannelTopic ch2() {
        return new ChannelTopic("ch2");
    }
}
