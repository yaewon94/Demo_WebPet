package com.example.demo_webPet.board.RescuedAnimal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
final class RescuedAnimalApiClient {

    private final WebClient webClient;

    @Value("${animal.api.key}")
    private String serviceKey;

    @Value("${animal.api.host}")
    private String apiHost;

    RescuedAnimalApiResponse getAnimalList(){
        final String API_PATH = "/1543061/abandonmentPublicService_v2/abandonmentPublic_v2";
        // TODO : page,size 실무 값으로 변경
        final int page = 1;
        final int size = 10;

        // 공공데이터 api 요청
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(apiHost)
                        .path(API_PATH)
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", page)
                        .queryParam("numOfRows", size)
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(RescuedAnimalApiResponse.class)
                .block();
    }
}
