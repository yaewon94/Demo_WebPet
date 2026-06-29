package com.example.demo_webPet.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    @Query("""
    select new com.example.demo_webPet.board.BoardCommentResponse(
        c.id,
        u.userName,
        c.content,
        c.createdAt
        )
    from BoardComment c
    join c.user u
    where c.boardType = :boardType
    and c.boardId = :boardId
    order by c.createdAt asc
    """)
    Page<BoardCommentResponse> findCommentList(
            @Param("boardType") BoardType boardType,
            @Param("boardId") Long boardId,
            Pageable pageable);
}
