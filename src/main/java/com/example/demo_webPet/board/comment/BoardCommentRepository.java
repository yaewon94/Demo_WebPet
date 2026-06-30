package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.Board;
import com.example.demo_webPet.board.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    @Query("""
    select new com.example.demo_webPet.board.comment.BoardCommentResponse(
        c.id,
        coalesce(u.userName, c.guestUserName),
        c.content,
        c.createdAt
        )
     from BoardComment c
     left join c.user u
     where c.boardType = :boardType
     and c.boardId = :boardId
     order by c.createdAt asc
    """)
    Page<BoardCommentResponse> findCommentList(
            @Param("boardType") BoardType boardType,
            @Param("boardId") Long boardId,
            Pageable pageable);

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
