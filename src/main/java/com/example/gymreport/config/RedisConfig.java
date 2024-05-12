package com.example.gymreport.config;

import com.example.gymreport.redis.model.TrainerSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String REDIS_HOST;
    @Value("${spring.data.redis.port}")
    private Integer REDIS_PORT;
    @Value("${spring.data.redis.username}")
    private String REDIS_USERNAME;
    @Value("${spring.data.redis.password}")
    private String REDIS_PASSWORD;


    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);
        config.setUsername(REDIS_USERNAME);
        config.setPassword(REDIS_PASSWORD);
        return new LettuceConnectionFactory(config);
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
    public HashOperations<String, String, TrainerSummary> hashOperations() {
        return redisTemplate().opsForHash();
    }
}
