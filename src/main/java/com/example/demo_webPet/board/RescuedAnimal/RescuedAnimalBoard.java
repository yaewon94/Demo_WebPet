package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.board.Board;
import com.example.demo_webPet.board.BoardType;
import com.example.demo_webPet.shelter.Shelter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name="TB_Board_RescuedAnimal")
final class RescuedAnimalBoard extends Board {

    @Embedded
    private Shelter shelter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(unique = true, nullable = false)
    private String desertionNo; // 외부 api 식별자

    @Column(nullable = false)
    private String noticeStartDate;

    @Column(nullable = false)
    private String noticeEndDate;

    private String imgUrl1;
    private String imgUrl2;

    RescuedAnimalBoard(){
        super(BoardType.RESCUED_ANIMAL);
        shelter = new Shelter(); // TODO : DB entity로 바꾸면 지우기
    }

    public void setCreatedAt(String createdAt){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime date = LocalDateTime.parse(createdAt, formatter);
        super.setCreatedAt(date);
    }

    void update(RescuedAnimalApiDto dto){
        update(dto, this);
        this.animal.update(dto);
    }

    static RescuedAnimalBoard from(RescuedAnimalApiDto dto){
        RescuedAnimalBoard board = new RescuedAnimalBoard();
        update(dto, board);
        return board;
    }

    private static void update(RescuedAnimalApiDto dto, RescuedAnimalBoard board){
        board.desertionNo = dto.desertionNo();
        board.noticeStartDate = dto.noticeSdt();
        board.noticeEndDate = dto.noticeEdt();
        board.imgUrl1 = dto.popfile1();
        board.imgUrl2 = dto.popfile2();
        board.setTitle(dto.noticeNo());
        board.setContent(dto.specialMark());
        board.setCreatedAt(dto.updTm());

        board.shelter.setId(dto.careRegNo());
        board.shelter.setName(dto.careNm());
        board.shelter.setTel(dto.careTel());
        board.shelter.setAddress(dto.careAddr());
    }
}
