package com.example.demo_webPet.shelter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO : DB entity로 변경
@Embeddable
@Getter
@Setter
public class Shelter {

    @Column(name="shelter_id")
    private String id;

    @Column(name="shelter_name")
    private String name;

    @Column(name="shelter_tel")
    private String tel;

    @Column(name="shelter_address")
    private String address;

    public Shelter(){}

    Shelter(Long id, String name){
        this.id = String.valueOf(id);
        this.name = name;
    }
}
