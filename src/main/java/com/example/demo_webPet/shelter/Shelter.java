package com.example.demo_webPet.shelter;
import com.example.demo_webPet.animal.Animal;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name="TB_Shelter")
public final class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "shelter") // DB컬럼명이 아닌 상대 엔티티의 필드명
    private List<Animal> animalList;
}
