package com.example.gymreport.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms
//@ConditionalOnProperty(name = "INTERACTION_BETWEEN_MICROSERVICES", havingValue = "activemq")
public class JmsConfig {

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        ObjectMapper customObjectMapper = new ObjectMapper();
        customObjectMapper.registerModule(new JavaTimeModule());

        // add custom classes to map for deserialization
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("TrainerWorkLoadRequest", com.example.gymreport.dto.TrainerWorkLoadRequest.class);

        // configuring converter
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter(customObjectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setTypeIdMappings(typeIdMappings);

        return converter;
    }
}
