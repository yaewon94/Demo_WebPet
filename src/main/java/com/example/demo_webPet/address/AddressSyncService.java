package com.example.demo_webPet.address;

import com.example.demo_webPet.shelter.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressSyncService {

    private final AddressApiClient apiClient;
    private final AddressRepository repository;
    private final ShelterService shelterService;

    @Transactional
    public void sync(){
        Map<String, Address> addressMap = repository.findAll().stream()
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
                if(sigungu.uprCd() != null) shelterService.sync(sigungu.uprCd(), sigungu.orgCd());
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
