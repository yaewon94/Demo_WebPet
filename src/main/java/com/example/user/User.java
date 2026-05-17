package com.example.user;

import com.example.adoption.AdoptionRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter(AccessLevel.PACKAGE)
    private final UserType type;

    @NotBlank
    @Column(nullable = false)
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "adoptionRequest")
    @Getter(AccessLevel.NONE)
    private List<AdoptionRequest> adoptionRequestList;

    public User(){
        this.type = UserType.NORMAL;
    }

    public User(UserType type){
        this.type = type;
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
