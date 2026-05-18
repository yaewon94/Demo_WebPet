package com.example.demo_webPet.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// [ record ]
// 디폴트 생성자 x
// 생성후 값 변경 불가능 (안전성 좋음) : Setter 없음
// Getter 어노테이션 필요 없음 : 객체.필드명() 이렇게 가져오면 됨
public record UserSignupDto(
        @NotBlank(message = UserConstants.MSG_INPUT_ID_REQUIRED)
        String id, // USER_ID_TYPE이 아닌 String으로 받아도 괜찮음. html로 들어오는건 모두 문자열로 받기 때문에

        @NotBlank(message = UserConstants.MSG_INPUT_PW_REQUIRED) // java, validation 레벨 검증
        @Column(nullable = false) // db 레벨 검증
        @Size(min = UserConstants.VALUE_MIN_PASSWORD_SIZE, max=UserConstants.VALUE_MAX_PASSWORD_SIZE, message = UserConstants.MSG_PW_SIZE_REQUIRED)
        String password
) {
    public static UserSignupDto getNewInstance() {
        return new UserSignupDto(null, null);
    }
}

/*Getter
//@Setter(AccessLevel.PACKAGE) // 값 받아오는게 안됨
@Setter
public class UserSignupDto {

    @NotBlank(message = UserConstants.MSG_INPUT_ID_REQUIRED)
    private String id;

    @NotBlank(message = UserConstants.MSG_INPUT_PW_REQUIRED) // java, validation 레벨 검증
    @Column(nullable = false) // db 레벨 검증
    @Size(min = UserConstants.VALUE_MIN_PASSWORD_SIZE, max=UserConstants.VALUE_MAX_PASSWORD_SIZE, message = UserConstants.MSG_PW_SIZE_REQUIRED)
    private String password;
}*/