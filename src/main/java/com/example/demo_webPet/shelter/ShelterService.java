package com.example.demo_webPet.shelter;

import com.example.demo_webPet.address.Address;
import com.example.demo_webPet.address.AddressRepository;
import com.example.demo_webPet.board.RescuedAnimal.RescuedAnimalApiDto;
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
@Transactional(readOnly = true)
public class ShelterService {

    private final ShelterApiClient apiClient;
    private final ShelterRepository repository;
    private final AddressRepository addressRepository;

    // 공공 보호소 api : 보호소 id, 이름 만 반환
    // 보호동물 api : 보호소 id, 이름, 주소, 전화번호 반환해서 (주소,전화번호) 업데이트 하려고 만듦
    public Shelter update(RescuedAnimalApiDto dto){
        Shelter shelter = repository.getReferenceById(dto.careRegNo());
        shelter.update(dto);
        return shelter;
    }

    public List<Shelter> getShelterList(){
        return repository.findAll();
    }

    public Shelter getShelter(String id){
        return repository.findById(id).orElse(null);
    }

    public void sync(String sidoCode, String sigunguCode){
        Map<String, Shelter> shelterMap = repository
                .findActiveShelters(sidoCode, sigunguCode)
                .stream()
                .collect(Collectors.toMap(
                        Shelter::getId,
                        Function.identity()
                ));
        List<ShelterDto> apiResult = apiClient.getShelterList(sidoCode, sigunguCode);
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
        repository.saveAll(shelterList);
    }

    // TODO : 보호동물 게시물 비활성화와 연동해서 호출
    private void deactivateUnusedShelters(){
        repository.deactivateUnusedShelters();
    }
}
