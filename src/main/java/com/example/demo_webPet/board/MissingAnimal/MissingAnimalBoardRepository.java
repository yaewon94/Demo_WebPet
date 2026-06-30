package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.board.BoardDto_forList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MissingAnimalBoardRepository extends JpaRepository<MissingAnimalBoard, Long> {

    @Query("""
    select new com.example.demo_webPet.board.BoardDto_forList(
        b.id,
        b.title,
        b.createdAt,
        u.userName
    )
    from MissingAnimalBoard b
    join b.user u
    """)
    Page<BoardDto_forList> findBoardList(Pageable pageable);

    @Query("""
    select new com.example.demo_webPet.board.MissingAnimal.MissingAnimalBoardDetailResponse(
            b.id,
            a.status,
            b.title,
            u.userName,
            b.createdAt,
            a.species,
            a.missingDate,
            a.missingLocation,
            b.content
    )
    from MissingAnimalBoard b
    join b.user u
    join b.animal a
    where b.id = :id
    """)
    Optional<MissingAnimalBoardDetailResponse> findDetailById(@Param("id") Long id);

    @Query("""
     select new com.example.demo_webPet.board.MissingAnimal.MissingAnimalBoardWriteRequest(
          b.id,
          b.title,
          a.species,
          a.missingDate,
          a.missingLocation,
          b.content)
     from MissingAnimalBoard b
     join b.animal a
     where b.id = :boardId
     and b.user.id = :userId
     """)
    Optional<MissingAnimalBoardWriteRequest> findModifyDtoById(Long boardId, Long userId);

    @Modifying
    @Query("""
    delete from MissingAnimalBoard b
    where b.id = :boardId
      and b.user.id = :userId
    """)
    int deleteByIdAndUserId(Long boardId, Long userId);
}
