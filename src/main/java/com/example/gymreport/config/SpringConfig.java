package com.example.gymreport.config;

import com.example.gymreport.handler.interceptor.CorrelationIdLoggerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SpringConfig implements WebMvcConfigurer {
    private final CorrelationIdLoggerInterceptor correlationIdLoggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(correlationIdLoggerInterceptor).addPathPatterns("/api/**");
    }
}
