package com.example.gymreport.config;

import com.example.gymreport.redis.model.TrainerSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, TrainerSummary> redisTemplate() {
        RedisTemplate<String, TrainerSummary> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());

        ObjectMapper objectMapper = new ObjectMapper();

        Jackson2JsonRedisSerializer<TrainerSummary> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, TrainerSummary.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public HashOperations<String, String, TrainerSummary> hashOperations(){
        return redisTemplate().opsForHash();
    }
}
