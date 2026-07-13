package com.example.demo_webPet.shelter;

import com.example.demo_webPet.board.RescuedAnimal.RescuedAnimalApiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShelterService {

    private final ShelterRepository repository;

    public Shelter findOrCreate(RescuedAnimalApiDto dto){
        Shelter shelter = repository.findById(dto.careRegNo()).orElseGet(() -> Shelter.from(dto));
        shelter.update(dto);
        return repository.save(shelter);
    }

    public List<Shelter> getShelterList(){
        return repository.findAll();
    }

    public Shelter getShelter(String id){
        return repository.findById(id).orElse(null);
    }

    // TODO : 보호동물 게시물 비활성화와 연동해서 호출
    public void deactivateUnusedShelters(){
        repository.deactivateUnusedShelters();
    }
}
