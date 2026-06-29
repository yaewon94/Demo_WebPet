package com.example.demo_webPet.board;

import com.example.demo_webPet.user.User;
import jakarta.persistence.*;
import lombok.Getter;
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
    private User user;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column(nullable = false, updatable = false)
    private Long boardId;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
