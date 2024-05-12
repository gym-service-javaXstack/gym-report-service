package com.example.gymreport.util;

import com.example.gymreport.exceptions.Error;
import com.example.gymreport.exceptions.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class FeignClientDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public Exception decode(String feignClientClassName, Response response) {
        if (response.status() >= HttpStatus.BAD_REQUEST.value()
                && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {

            String responseBody;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
                responseBody = reader.lines().collect(Collectors.joining("\n"));
                Error error = objectMapper.readValue(responseBody, Error.class);

                if (response.status() == HttpStatus.BAD_REQUEST.value()){
                    throw new BadRequestException(error.message());
                }

                if (response.status() == HttpStatus.UNAUTHORIZED.value() || response.status() == HttpStatus.FORBIDDEN.value()){
                    throw new UnauthorizedException(error.message(), error.errorType(), error.timeStamp());
                }
            } catch (IOException e) {
                log.error("Error reading response body", e);
            }
        }
        return defaultErrorDecoder.decode(feignClientClassName, response);
    }
}
