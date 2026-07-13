package com.example.demo_webPet.address;

record AddressDto(
        String orgCd, // 기관 코드
        String orgdownNm, // 기관명
        String uprCd // 상위 코드 (ex. orgdownNM = 서초구 인 경우 uprCd = 서울특별시의 코드)
) {
}
