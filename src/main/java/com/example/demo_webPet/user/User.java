package com.example.demo_webPet.user;

import com.example.demo_webPet.adoption.AdoptionRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
//@Setter(AccessLevel.PACKAGE) // 별로 의미 없음 : JPA는 reflection으로 필드 접근 가능, 같은 클래스 내부 메서드로 변경 가능, 서비스 레이어에서 우회 가능하므로
public final class User {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    //@Getter(AccessLevel.PACKAGE) // 이미 클래스에 getter어노테이션을 선언하면 필드는 접근제한을 따로 설정한다고 해도 적용 안됨
    private final UserType type;

    private String password;

    @NotNull
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "user")
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
