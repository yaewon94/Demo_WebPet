package com.example.demo_webPet.board;

import com.example.demo_webPet.common.constants.CommonValidConstants;
import com.example.demo_webPet.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class Board
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @Setter(AccessLevel.PUBLIC)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = CommonValidConstants.MAX_SIZE_TITLE)
    private String title;

    @Column(nullable = false)
    @Setter(AccessLevel.PUBLIC)
    private String content;

    // TODO : 댓글 리스트

    protected Board(){}

    public void setTitle(String title){
        // 최대 글자수를 넘으면 잘라서 db에 저장
        if(title == null) return;
        this.title = title.substring(
                0,
                Math.min(title.length(), CommonValidConstants.MAX_SIZE_TITLE));
    }

    protected void update(String title, String content){
        setTitle(title);
        setContent(content);
    }
}
