package com.example.demo_webPet.board.comment;

import com.example.demo_webPet.board.BoardType;
import com.example.demo_webPet.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name="TB_Board_Comments",
        indexes = {@Index(
                name = "idx_board_comment",
                columnList = "boardType, boardId"
        )})
class BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @Setter(AccessLevel.PACKAGE)
    private User user;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.PACKAGE)
    private BoardType boardType;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PACKAGE)
    private Long boardId;

    @Column(nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
