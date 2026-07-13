package com.example.demo_webPet.shelter;

import java.util.List;

record ShelterApiResponse(
        Response response
) {

    public record Response(
            Header header,
            Body body
    ) {
    }

    public record Header(
            String errorMsg,
            String reqNo,
            String resultCode,
            String resultMsg
    ) {}

    public record Body(
            String pageNo,
            Items items,
            String totalCount,
            String numOfRows
    ) {}

    public record Items(
            List<ShelterDto> item
    ) {}
}