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
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String tel;

    @Column(nullable = false)
    private String detailAddress;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(nullable = false)
    private LocalDate updateDate;

    @Column(nullable = false)
    private boolean isActive = true;

    @PrePersist
    @PreUpdate
    private void updateDate() {
        setUpdateDate(LocalDate.now());
    }

    public Shelter(){}

    static Shelter from(RescuedAnimalApiDto dto){
        Shelter shelter = new Shelter();
        shelter.setName(dto.careNm());
        shelter.setTel(dto.careTel());
        shelter.setDetailAddress(dto.careAddr());
        return shelter;
    }

    public void update(RescuedAnimalApiDto dto){
        setName(dto.careNm());
        setTel(dto.careTel());
        setDetailAddress(dto.careAddr());
    }
}
