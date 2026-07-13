package com.example.demo_webPet.shelter;

import com.example.demo_webPet.address.AddressService;
import com.example.demo_webPet.board.RescuedAnimal.RescuedAnimalApiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository repository;
    private final AddressService addressService;

    // 공공 보호소 api : 보호소 id, 이름 만 반환
    // 보호동물 api : 보호소 id, 이름, 주소, 전화번호 반환해서 (주소,전화번호) 업데이트 하려고 만듦
    public Shelter createOrUpdate(RescuedAnimalApiDto dto){
        Shelter shelter = repository.findById(dto.careRegNo())
                .orElseGet(()->Shelter.from(
                        dto,
                        addressService.findByAddress(dto.careAddr())));
        shelter.update(dto);
        Shelter result = repository.save(shelter);
        return result;
    }

    @Transactional(readOnly = true)
    public void update(RescuedAnimalApiDto dto){
        repository.getReferenceById(dto.careRegNo()).update(dto);
    }

    @Transactional(readOnly = true)
    public List<Shelter> getShelterList(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Shelter getShelter(String id){
        return repository.findById(id).orElse(null);
    }
}
