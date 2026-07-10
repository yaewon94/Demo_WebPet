package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.board.Board;
import com.example.demo_webPet.board.BoardType;
import com.example.demo_webPet.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name="TB_Board_MissingAnimal")
final class MissingAnimalBoard extends Board {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @Setter(AccessLevel.PACKAGE)
    private User user;

    // cascade : JPA 에서 해당 엔티티를 자동으로 생성,수정,삭제
    // - PERSIST(insert, update), MERGE(준영속 => 영속상태), REMOVE(delete)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    MissingAnimalBoard(){
        super(BoardType.MISSING_ANIMAL);
    }
}
