package com.example.votingsystem.http;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author samwel.wafula
 * Created on 12/01/2024
 * Time 12:49
 * Project VotingSystem
 */
@RequiredArgsConstructor
@Component
public class HttpHandler {

    private final WebClient webClient;

    public String SendApiCallRequest(String url, HttpMethod method){
        return webClient.method(method)
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public Object sendSyncCallWithBody(String url,HttpMethod httpMethod,Object body){
        //MultiValueMap<String, String> finalHeaders = new LinkedMultiValueMap<>();
        return webClient.method(httpMethod)
                .uri(url)
                .bodyValue(body)
                .header("Accept","*/*")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
