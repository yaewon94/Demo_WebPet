package com.example.demo_webPet.animal;

import com.example.demo_webPet.board.MissingAnimal.MissingAnimalBoardWriteRequest;
import com.example.demo_webPet.board.RescuedAnimal.RescuedAnimalApiDto;
import com.example.demo_webPet.common.util.ValidationCheck;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name="TB_Animal")
public final class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    // EnumType.ORDINAL을 쓸 경우 enum 순서가 바뀌면 DB에 저장된거랑 달라져 데이터 꼬이기 때문에 치명적
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalSpecies species = AnimalSpecies.DOG;

    @Column(nullable = false)
    private String breed; // 품종 이름

    @Column(nullable = false)
    private String birthYear;

    @Column(nullable = false)
    private String weight;

    @Enumerated(EnumType.STRING)
    private AnimalGender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalStatus status;

    private LocalDate missingDate;

    // 특별시,광역시,도
    private String missingAdrs1;
    // 시,구
    private String missingAdrs2;

    private String rescuedDate;

    private String rescuedAdrs;

    private String shelter_id;

    void setGender(String gender) {
        if(gender.equalsIgnoreCase("M")){
            this.gender = AnimalGender.MALE;
        }else if(gender.equalsIgnoreCase("F")){
            this.gender = AnimalGender.FEMALE;
        }
    }

    void setStatus(String status) {
        if(status.contains("보호")){
            this.status = AnimalStatus.PROTECTING;
        }
    }

    void setSpecies(String species) {
        if(species.equals("개")){
            this.species = AnimalSpecies.DOG;
        }else if(species.equals("고양이")){
            this.species = AnimalSpecies.CAT;
        }else{
            this.species = AnimalSpecies.ETC;
        }
    }

    @PrePersist
    @PreUpdate
    private void validateDate() {
        ValidationCheck.validateNotFutureDate(missingDate, "실종날짜");
        //ValidationCheck.validateNotFutureDate(rescuedDate, "구조날짜");
    }

    public static Animal from(MissingAnimalBoardWriteRequest dto){
        Animal animal = new Animal();
        animal.status = AnimalStatus.MISSING;
        updateAnimal(dto, animal);
        return animal;
    }

    public static Animal from(RescuedAnimalApiDto dto){
        Animal animal = new Animal();
        updateAnimal(dto, animal);
        return animal;
    }

    public void update(MissingAnimalBoardWriteRequest dto){
        updateAnimal(dto, this);
    }

    public void update(RescuedAnimalApiDto dto){
        updateAnimal(dto, this);
    }

    private static void updateAnimal(MissingAnimalBoardWriteRequest dto, Animal animal){
        animal.species = dto.species();
        animal.setMissingDate(dto.missingDate());
        animal.setMissingAdrs1(dto.address1());
        animal.setMissingAdrs2(dto.address2());
    }

    private static void updateAnimal(RescuedAnimalApiDto dto, Animal animal){
        animal.setRescuedDate(dto.happenDt());
        animal.setRescuedAdrs(dto.happenPlace());
        animal.setSpecies(dto.upKindNm());
        animal.setGender(dto.sexCd());
        animal.setBirthYear(dto.age());
        animal.setStatus(dto.processState());
        animal.setWeight(dto.weight());
        animal.setBreed(dto.kindNm());
        animal.setShelter_id(dto.careRegNo());
    }
}
