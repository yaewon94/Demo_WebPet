package com.example.demo_webPet.address;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
final class AddressApiClient {

    private final WebClient webClient;

    @Value("${animal.api.key}")
    private String serviceKey;

    @Value("${animal.api.host}")
    private String apiHost;

    AddressApiResponse getSido(){
        final String API_PATH = "/1543061/abandonmentPublicService_v2/sido_v2";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(apiHost)
                        .path(API_PATH)
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("_type", "json")
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 100)
                        .build())
                .retrieve()
                .bodyToMono(AddressApiResponse.class)
                .block();
    }

    AddressApiResponse getSigungu(String sidoCode){
        final String API_PATH = "/1543061/abandonmentPublicService_v2/sigungu_v2";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(apiHost)
                        .path(API_PATH)
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("upr_cd", sidoCode)
                        .queryParam("_type", "json")
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 100)
                        .build())
                .retrieve()
                .bodyToMono(AddressApiResponse.class)
                .block();
    }
}
