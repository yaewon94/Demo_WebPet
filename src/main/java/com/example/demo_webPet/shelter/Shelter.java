package com.example.demo_webPet.shelter;

import com.example.demo_webPet.address.Address;
import com.example.demo_webPet.board.RescuedAnimal.RescuedAnimalApiDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="TB_Shelter")
public class Shelter {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String tel;

    private String detailAddress;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(nullable = false)
    private boolean isActive = true;

    public Shelter(){}

    static Shelter from(ShelterDto dto, Address address){
        Shelter shelter = new Shelter();
        shelter.setId(dto.careRegNo());
        shelter.setName(dto.careNm());
        shelter.setAddress(address);
        return shelter;
    }

    static Shelter from(RescuedAnimalApiDto dto, Address address){
        Shelter shelter = new Shelter();
        shelter.setId(dto.careRegNo());
        shelter.setName(dto.careNm());
        shelter.setTel(dto.careTel());
        shelter.setDetailAddress(dto.careAddr());
        shelter.setAddress(address);
        return shelter;
    }

    void update(RescuedAnimalApiDto dto){
        setName(dto.careNm());
        setTel(dto.careTel());
        setDetailAddress(dto.careAddr());
    }

    void update(ShelterDto dto){
        setName(dto.careNm());
    }
}
