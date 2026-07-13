package com.example.demo_webPet.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public Address findByAddress(String address){
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("주소가 비었습니다");
        }

        // 문자열 파싱
        String[] parts = address.trim().split("\\s+");
        if (parts.length < 2) {
            throw new IllegalArgumentException("잘못된 주소 형식입니다 : " + address);
        }
        String sido = parts[0];
        String sigungu = parts[1];

        // DB 조회
        return repository.findBySidoAndSigungu(sido, sigungu);
    }
}
