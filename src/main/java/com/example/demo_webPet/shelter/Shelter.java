package com.example.demo_webPet.shelter;
import com.example.demo_webPet.animal.Animal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public final class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotBlank
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "shelter")
    @Getter(AccessLevel.PACKAGE)
    private List<Animal> animalList;
}
