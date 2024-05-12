package com.example.gymreport.config;


import com.example.gymreport.util.FeignClientDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.example.gymreport.feign")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class FeignConfig {

    @Bean
    public ErrorDecoder myErrorDecoder() {
        return new FeignClientDecoder();
    }
}
