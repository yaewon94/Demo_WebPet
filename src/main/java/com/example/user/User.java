package com.example.user;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private String password;

    private String phone;

    public User(){
        this.type = UserType.NORMAL;
    }

    public enum UserType{
        NORMAL("개인회원"),
        SHELTER("보호소 계정");

        private final String label;

        UserType(String label){
            this.label = label;
        }

        public String GetLabel(){
            return label;
        }
    }
}
