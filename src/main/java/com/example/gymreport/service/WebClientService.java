package com.example.gymreport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebClientService {
    private final WebClient webClient;

    public Mono<String> requestDataFromOtherMicroservice(String jwtToken) {
        return webClient.get()
                .uri("http://gym-main/api/v1/token")
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(String.class);
    }
}
