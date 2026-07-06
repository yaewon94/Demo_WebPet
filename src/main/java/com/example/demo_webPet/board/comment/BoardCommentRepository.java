package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    // package 안에서만 사용
    Page<BoardComment> findByBoardTypeAndBoardId(
            @Param("boardType") BoardType boardType,
            @Param("boardId") Long boardId,
            Pageable pageable);

    @Modifying
    @Query("""
    delete from BoardComment c
    where c.boardType = :boardType
      and c.boardId = :boardId
    """)
    void deleteCommentsAll(@Param("boardType") BoardType boardType,
                           @Param("boardId") Long boardId);

    /*@Query("""
    select count(b)
    from Board b // 문제 : Board는 추상클래스. 다 상속받은걸 구체클래스로 만듦. Board마다 사용 필드가 달라서 통일 못함
    where b.boardType // 문제 : Board에 boardType 필드를 만들어놓지 않았다
    and b.id = :boardId
    """)
    boolean existsBoard(
            @Param("boardType") BoardType boardType,
            @Param("boardId") Long boardId);*/
}
