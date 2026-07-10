package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.board.BoardDto_forList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface RescuedAnimalBoardRepository extends JpaRepository<RescuedAnimalBoard, Long> {

    @Query("""
    select b from RescuedAnimalBoard b
    where b.desertionNo = :desertionNo
    """)
    RescuedAnimalBoard getByDesertionNo(String desertionNo);

    @Query("""
    select new com.example.demo_webPet.board.BoardDto_forList(
        b.id,
        b.title,
        b.createdAt,
        b.shelter.name,
        b.imgUrl1
    )
    from RescuedAnimalBoard b
    """)
    Page<BoardDto_forList> findBoardList(Pageable pageable);
}
