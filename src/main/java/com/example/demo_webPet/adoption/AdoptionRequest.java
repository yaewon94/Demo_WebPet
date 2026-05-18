package com.example.demo_webPet.adoption;

import com.example.demo_webPet.animal.Animal;
import com.example.demo_webPet.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;

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