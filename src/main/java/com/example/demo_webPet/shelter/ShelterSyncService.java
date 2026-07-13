package com.example.demo_webPet.shelter;

import com.example.demo_webPet.address.Address;
import com.example.demo_webPet.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
public class ShelterSyncService {

    private final ShelterApiClient apiClient;
    private final ShelterRepository shelterRepository;
    private final AddressRepository addressRepository;

    // @ cron = 초 분 시 일 월 요일
    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void syncAllData() {
        sync();
    }

    private void sync(){
        List<Address> addressList = addressRepository.findByParentIdIsNotNull();
        for(Address address : addressList){
            sync(address.getParentId(), address.getId());
        }
    }

    private void sync(String sidoCode, String sigunguCode){
        List<ShelterDto> apiResult = apiClient.getShelterList(sidoCode, sigunguCode);
        if(apiResult == null) return;

        Map<String, Shelter> shelterMap = shelterRepository
                .findActiveShelters(sidoCode, sigunguCode)
                .stream()
                .collect(Collectors.toMap(
                        Shelter::getId,
                        Function.identity()
                ));
        List<Shelter> shelterList = new ArrayList<>();
        Address address = addressRepository.getReferenceById(sigunguCode);

        // update, or add entity
        for(ShelterDto dto : apiResult){
            Shelter entity = shelterMap.get(dto.careRegNo());

            if(entity != null){
                entity.update(dto);
            }else{
                Shelter newEntity = Shelter.from(dto, address);
                shelterList.add(newEntity);
                shelterMap.put(newEntity.getId(), newEntity);
            }
        }

        // DB 저장
        shelterRepository.saveAll(shelterList);
    }

    // TODO : 보호동물 게시물 비활성화와 연동해서 호출
    @Transactional
    private void deactivateUnusedShelters(){
        shelterRepository.deactivateUnusedShelters();
    }
}
