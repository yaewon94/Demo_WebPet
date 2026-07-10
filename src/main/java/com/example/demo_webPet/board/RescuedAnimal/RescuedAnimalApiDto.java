package com.example.demo_webPet.board.RescuedAnimal;

public record RescuedAnimalApiDto(
     String desertionNo, // 동물번호
     String happenDt, // 발견날짜
     String happenPlace, // 발견장소
     String upKindNm, // 종류 (개, 고양이, 기타)
     String kindNm, // 품종 (불독, 말티즈, ...)
     String age, // 나이 (년생)
     String weight, // 몸무게 (kg)
     String noticeNo, // 공고 번호
     String noticeSdt, // 공고 시작날짜
     String noticeEdt, // 공고 종료날짜
     String popfile1, // 이미지 url
     String popfile2, // 이미지 url
     String processState, // 상태 (Notice:공고, Protected:보호중)
     String sexCd, // 성별 (M:수컷, F:암컷, Q:미확인)
     String specialMark, // 특징
     String careRegNo, // 보호소 코드
     String careNm, // 보호소 이름
     String careTel, // 보호소 전화번호
     String careAddr, // 보호소 주소
     String updTm // 작성날짜(수정날짜)
) {
}
