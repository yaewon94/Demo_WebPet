package com.example.demo_webPet.board.RescuedAnimal;

import lombok.Getter;

import java.util.List;

@Getter
final class RescuedAnimalApiResponse {

    private Response response;

    @Getter
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    public static class Items {
        private List<RescuedAnimalApiDto> item;
    }
}
