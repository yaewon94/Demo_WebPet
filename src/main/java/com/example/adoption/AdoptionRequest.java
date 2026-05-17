package com.example.adoption;

import com.example.animal.Animal;
import com.example.user.User;
import jakarta.persistence.*;

@Entity
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Animal animal;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
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