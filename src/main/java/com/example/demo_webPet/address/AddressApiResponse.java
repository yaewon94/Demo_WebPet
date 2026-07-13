package com.example.demo_webPet.address;

import java.util.List;

record AddressApiResponse(
        Response response
) {
    public record Response(
            Header header,
            Body body
    ) {
    }

    public record Header(
            String reqNo,
            String resultCode,
            String resultMsg,
            String errorMsg
    ) {
    }

    public record Body(
            Items items,
            int numOfRows,
            int pageNo,
            int totalCount
    ) {
    }

    public record Items(
            List<AddressDto> item
    ) {
    }
}
