package com.example.demo_webPet.board;

import com.example.demo_webPet.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
abstract class Board
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String title;

    private String content;

    void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(createdAt + " : PARAM ERROR");
        }

        this.createdAt = createdAt;
    }
}
