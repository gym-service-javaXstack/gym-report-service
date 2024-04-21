package com.example.gymreport.service.web;

import reactor.core.publisher.Mono;

public interface WebClientService {
    Mono<String> requestDataFromOtherMicroservice(String jwtToken);
}
