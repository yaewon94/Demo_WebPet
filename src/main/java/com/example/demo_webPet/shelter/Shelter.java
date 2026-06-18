package com.example.demo_webPet.shelter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Shelter {

    @Column(name="shelter_id")
    private Long id;

    @Column(name="shelter_name_snapshot")
    private String nameSnapshot;

    public Shelter() {
        this.id = -1L;
        this.nameSnapshot = null;
    }

    Shelter(Long id, String name){
        this.id = id;
        this.nameSnapshot = name;
    }
}
