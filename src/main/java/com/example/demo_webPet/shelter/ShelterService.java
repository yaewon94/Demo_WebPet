package com.example.demo_webPet.shelter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShelterService {

    public List<ShelterDto> getShelterList(){
        // TODO : 외부 API를 통해 실제 우리나라 동물보호소 리스트 가져오기
        List<ShelterDto> list = new ArrayList<>();
        list.add(new ShelterDto(0L, "test보호소")); // TEST
        return list;
    }

    public Shelter getShelter(Long id){
        // TODO : 외부 API를 통해 실제 우리나라 동물보호소 리스트 가져오기
        // TODO : 기존 id-name 과 외부 api의 id-name이 맞는지 체크
        return new Shelter(id, "test보호소");
    }
}
