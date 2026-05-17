package com.example.adoption;

import com.example.animal.Animal;
import com.example.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(AccessLevel.PACKAGE)
public final class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name="animal_id", nullable = false)
    private Animal animal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NONE;

    public enum Status{
        NONE(""),
        PENDING("대기 중"),
        APPROVED("승인됨"),
        REJECTED("거절됨");

        private final String label;

        Status(String label){
            this.label = label;
        }

        public String GetLabel(){
            return label;
        }
    }
}