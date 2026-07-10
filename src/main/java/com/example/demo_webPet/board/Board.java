package com.example.demo_webPet.board;

import com.example.demo_webPet.common.constants.CommonValidConstants;
import com.example.demo_webPet.common.util.ValidationCheck;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class Board
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient // DB에 저장하지 않는 필드
    private final BoardType type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = CommonValidConstants.MAX_SIZE_TITLE)
    private String title;

    @Column(nullable = false)
    @Setter(AccessLevel.PUBLIC)
    private String content;

    protected Board(BoardType type){
        this.type = type;
    }

    protected final void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setTitle(String title){
        // 최대 글자수를 넘으면 잘라서 db에 저장
        if(title == null) return;
        this.title = title.substring(
                0,
                Math.min(title.length(), CommonValidConstants.MAX_SIZE_TITLE));
    }

    public final void update(String title, String content){
        setTitle(title);
        setContent(content);
    }

    // DB 업데이트 직전 호출
    @PrePersist
    private void prePersist() {
        if(this.createdAt == null){
            this.createdAt = LocalDateTime.now();
        }
        else{
            ValidationCheck.validateNotFutureDate(createdAt, "작성일");
        }
    }
}
