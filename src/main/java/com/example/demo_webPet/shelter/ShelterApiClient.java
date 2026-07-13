package com.example.demo_webPet.shelter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

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

        int page = 1;
        int total = 0;
        List<ShelterDto> result = new ArrayList<>();

        while(true){
            int currentPage = page;
            ShelterApiResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host(apiHost)
                            .path(API_PATH)
                            .queryParam("serviceKey", serviceKey)
                            .queryParam("_type", "json")
                            .queryParam("upr_cd", sidoCode)
                            .queryParam("org_cd", sigunguCode)
                            .queryParam("pageNo", currentPage)
                            .queryParam("numOfRows", 100)
                            .build())
                    .retrieve()
                    .bodyToMono(ShelterApiResponse.class)
                    .block();

            // 리스트에 추가
            if (response == null
                    || response.response() == null
                    || response.response().body() == null) {
                break;
            }

            var items = response.response().body().items();
            if (items != null && items.item() != null) {
                result.addAll(items.item());
            }

            // 페이지 넘김 여부 체크
            if(page == 1){
                total = Integer.parseInt(response.response().body().totalCount());
            }
            if (result.size() >= total) {
                break;
            }
            ++page;
        }

        return result;
    }
}
