package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.board.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name="TB_Board_MissingAnimal")
final class MissingAnimalBoard extends Board {

    // cascade : JPA 에서 해당 엔티티를 자동으로 생성,수정,삭제
    // - PERSIST(insert, update), MERGE(준영속 => 영속상태), REMOVE(delete)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    // TODO
    //@Column(nullable = false)
    private String imgUrl;

    public void update(String title, String content, Animal animal, String imgUrl){
        super.update(title, content);
        setAnimal(animal);
        setImgUrl(imgUrl);
    }
}
