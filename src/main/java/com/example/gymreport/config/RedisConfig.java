package com.example.gymreport.config;

import com.example.gymreport.redis.model.TrainerSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
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


        // Настраиваем ObjectMapper для работы с полиморфными подтипами и игнорированием проблем с отсутствующими свойствами
        ObjectMapper objectMapper = new ObjectMapper();

        // Указываем Jackson2JsonRedisSerializer для значений типа Object
        Jackson2JsonRedisSerializer<TrainerSummary> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, TrainerSummary.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }
}
