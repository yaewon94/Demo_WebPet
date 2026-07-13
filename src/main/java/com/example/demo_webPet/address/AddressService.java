package com.example.demo_webPet.address;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressApiClient apiClient;
    private final AddressRepository repository;

    // @ cron = 초 분 시 일 월 요일
    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional // public 이여야 spring proxy를 거침
    public void updateAddresses() {
        sync();
    }

    private void sync(){
        List<Address> addresses = repository.findAll();
        Map<String, Address> addressMap = addresses.stream()
                        .collect(Collectors.toMap(
                                Address::getId,
                                Function.identity()
                        ));
        List<AddressDto> sidoList = apiClient.getSido().response().body().items().item();
        List<Address> entityList = new ArrayList<>();

        for(AddressDto sido : sidoList){
            saveOrUpdate(sido, addressMap, entityList);
            // 해당 시,도의 시군구에도 적용
            List<AddressDto> sigunguList = apiClient.getSigungu(sido.orgCd()).response().body().items().item();
            if(sigunguList == null) continue;

            for(AddressDto sigungu : sigunguList){
                saveOrUpdate(sigungu, addressMap, entityList);
            }
        }

        // DB 저장 및 안쓰는 데이터 삭제
        repository.saveAll(entityList);
        repository.deleteOldAddresses();
    }

    // 데이터 규모 : 약 200~300건
    private void saveOrUpdate(
            AddressDto dto,
            Map<String, Address> addressMap,
            List<Address> entityList
    ){
        Address entity = addressMap.get(dto.orgCd());
        if(entity == null){
            if (dto.orgdownNm() == null || dto.orgdownNm().isBlank()) {
                System.out.println("기관명 없음");
                System.out.println("부모=" + dto.uprCd());
                return;
            }

            Address newEntity = Address.from(dto);
            entityList.add(newEntity);
            addressMap.put(newEntity.getId(), newEntity);
        }else{
            entity.update(dto);
        }
    }
}
