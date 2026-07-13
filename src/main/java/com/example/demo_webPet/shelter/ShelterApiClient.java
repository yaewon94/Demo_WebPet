package com.example.demo_webPet.shelter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
final class ShelterApiClient {

    @Value("${animal.api.key}")
    private String serviceKey;

    @Value("${animal.api.host}")
    private String apiHost;

    private final WebClient webClient;

    List<ShelterDto> getShelterList(String sidoCode, String sigunguCode){
        final String API_PATH = "/1543061/abandonmentPublicService_v2/shelter_v2";

        ShelterApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(apiHost)
                        .path(API_PATH)
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(ShelterApiResponse.class)
                .block();

        return Objects.requireNonNull(response).body().items().item();
    }
}
