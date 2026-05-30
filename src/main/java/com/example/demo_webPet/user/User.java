package com.example.demo_webPet.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.example.demo_webPet.user.UserConstants.PATTERN_REGEXP_USER_ID;

@Entity
@Getter
@Setter
@Table(name = "TB_user")
//@Setter(AccessLevel.PACKAGE) // 별로 의미 없음 : JPA는 reflection으로 필드 접근 가능, 같은 클래스 내부 메서드로 변경 가능, 서비스 레이어에서 우회 가능하므로
public final class User {

    //@EmbeddedId
    // PRIMARY KEY 를 기본타입이 아닌 클래스타입으로 하게 되면
    // Serializable 을 상속받아서 equals(), hashCode() 오버라이딩 해야되서 번거로움
    //private USER_ID_TYPE id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Pattern(regexp = PATTERN_REGEXP_USER_ID)
    @Column(nullable = false, unique = true) // DB 레벨에서의 검증
    private String userId;

    @Column(nullable = false)
    //@Getter(AccessLevel.PACKAGE) // 이미 클래스에 getter어노테이션을 선언하면 필드는 접근제한을 따로 설정한다고 해도 적용 안됨
    @Enumerated(EnumType.STRING)
    private final UserType type;

    @NotBlank
    @Column(nullable = false)
    private String password;

    /*private String phone;

    @OneToMany(mappedBy = "user")
    private List<AdoptionRequest> adoptionRequestList;*/

    // TODO : 가입날짜, 탈퇴날짜 추가 (탈퇴회원 구분 별도)

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
