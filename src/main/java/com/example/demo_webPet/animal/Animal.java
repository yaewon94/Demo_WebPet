package com.example.demo_webPet.animal;

import com.example.demo_webPet.board.MissingAnimal.MissingAnimalBoardWriteRequest;
import com.example.demo_webPet.common.util.ValidationCheck;
import com.example.demo_webPet.shelter.Shelter;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Table(name="TB_Animal")
public final class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    // EnumType.ORDINAL을 쓸 경우 enum 순서가 바뀌면 DB에 저장된거랑 달라져 데이터 꼬이기 때문에 치명적
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalSpecies species = AnimalSpecies.DOG;

    //@Min(0) // java validation 레벨의 검증임
    //@Max(100)
    private Integer age = 0;

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

    private LocalDate rescuedDate;
    private String foundLocation;
    @Embedded
    private Shelter shelter;

    void setAge(Integer age) {
        if(age == null){
            this.age = null;
        }
        else if(age < AnimalConstants.MIN_AGE || age > AnimalConstants.MAX_AGE){
            throw new IllegalArgumentException("동물 나이가 알맞지 않습니다 : " + age);
        }
        this.age = age;
    }

    @PrePersist
    @PreUpdate
    private void validateDate() {
        ValidationCheck.validateNotFutureDate(missingDate, "실종날짜");
        ValidationCheck.validateNotFutureDate(rescuedDate, "구조날짜");
    }

    public static Animal from(MissingAnimalBoardWriteRequest dto){
        Animal animal = new Animal();
        animal.species = dto.species();
        animal.status = AnimalStatus.MISSING;
        animal.missingDate = dto.missingDate();
        animal.missingAdrs1 = dto.address1();
        animal.missingAdrs2 = dto.address2();
        return animal;
    }

    public void update(AnimalSpecies species, LocalDate missingDate, String missingAdrs1, String missingAdrs2){
        this.species = species;
        this.missingDate = missingDate;
        this.missingAdrs1 = missingAdrs1;
        this.missingAdrs2 = missingAdrs2;
    }
}
