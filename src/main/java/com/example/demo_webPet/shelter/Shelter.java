package com.example.demo_webPet.shelter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Shelter {

    @Column(nullable = false, name="shelter_id")
    private Long id;

    @Column(nullable = false, name="shelter_name_snapshot")
    private String name_snapshot;

    public Shelter() {
        this.id = -1L;
        this.name_snapshot = null;
    }

    Shelter(Long id, String name){
        this.id = id;
        this.name_snapshot = name;
    }
}
