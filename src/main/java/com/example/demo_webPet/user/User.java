package com.example.demo_webPet.user;

import com.example.demo_webPet.shelter.Shelter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static com.example.demo_webPet.user.UserConstants.PATTERN_REGEXP_USER_NAME;

// TODO : 쪽지 기능추가

@Entity
@Getter
@Table(name = "TB_user")
public final class User {

    //@EmbeddedId
    // PRIMARY KEY 를 기본타입이 아닌 클래스타입으로 하게 되면
    // Serializable 을 상속받아서 equals(), hashCode() 오버라이딩 해야되서 번거로움
    //private USER_ID_TYPE id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Pattern(regexp = PATTERN_REGEXP_USER_NAME)
    @Column(nullable = false, unique = true, name="user_name") // DB 레벨에서의 검증
    @Setter(AccessLevel.PACKAGE)
    private String userName;

    @Column(nullable = false)
    //@Getter(AccessLevel.PACKAGE) // 이미 클래스에 getter어노테이션을 선언하면 필드는 접근제한을 따로 설정한다고 해도 적용 안됨
    @Enumerated(EnumType.STRING)
    private final UserType type;

    @Embedded
    private Shelter shelter;

    @NotBlank
    @Column(nullable = false)
    @Setter(AccessLevel.PACKAGE)
    @JsonIgnore
    private String password;

    /*private String phone;

    @OneToMany(mappedBy = "user")
    private List<AdoptionRequest> adoptionRequestList;*/

    // TODO : 가입날짜, 탈퇴날짜 추가 (탈퇴회원 구분 별도)

    User(){
        this.type = UserType.NORMAL;
    }
    User(UserType type){
        this.type = type;
    }

    void setShelter(Shelter shelter) {
        if(type == UserType.SHELTER){
            if(shelter == null){
                throw new IllegalArgumentException("보호소를 선택해 주세요");
            }
            this.shelter = shelter;
        }
        else{
            this.shelter = null;
        }
    }
}
