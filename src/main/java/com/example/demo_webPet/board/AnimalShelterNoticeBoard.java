package com.example.demo_webPet.board;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.shelter.Shelter;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name="TB_Board_AnimalShelterNotice")
final class AnimalShelterNoticeBoard extends Board
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
}
