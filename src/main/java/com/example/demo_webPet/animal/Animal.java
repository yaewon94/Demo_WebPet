package com.example.demo_webPet.animal;

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
    @Column(nullable = false)
    private AnimalGender gender = AnimalGender.MALE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalStatus status;

    @Embedded
    private Shelter shelter;

    @Column(nullable = false)
    private LocalDate rescuedDate;

    @Column(nullable = false)
    // TODO : 지도 표시
    private String foundLocation;

    @Column(nullable = false)
    private String imgPath;

    /*@OneToMany(mappedBy = "animal")
    @Getter(AccessLevel.NONE)
    private List<AdoptionRequest> adoptionRequestList;*/

    void setAge(Integer age) {
        if(age < AnimalConstants.MIN_AGE || age > AnimalConstants.MAX_AGE){
            throw new IllegalArgumentException(age + " : PARAM ERROR");
        }
        this.age = age;
    }

    void setRescuedDate(LocalDate rescuedDate) {
        if (rescuedDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(rescuedDate + " : PARAM ERROR");
        }
        this.rescuedDate = rescuedDate;
    }
}
