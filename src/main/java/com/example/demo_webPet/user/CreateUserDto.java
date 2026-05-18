package com.example.demo_webPet.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.example.demo_webPet.user.UserConstants.MSG_USER_ID_PATTERN_CONSTRAINT;
import static com.example.demo_webPet.user.UserConstants.PATTERN_REGEXP_USER_ID;

// [ record ]
// 디폴트 생성자 x
// 생성후 값 변경 불가능 (안전성 좋음) : Setter 없음
// Getter 어노테이션 필요 없음 : 객체.필드명() 이렇게 가져오면 됨
public record CreateUserDto(
        @NotBlank(message = UserConstants.MSG_INPUT_ID_REQUIRED)
        @Size(min = UserConstants.VALUE_MIN_USER_ID_SIZE, max=UserConstants.VALUE_MAX_USER_ID_SIZE, message = UserConstants.MSG_USER_ID_SIZE_REQUIRED)
        @Pattern(regexp = PATTERN_REGEXP_USER_ID, message = MSG_USER_ID_PATTERN_CONSTRAINT) // 영어, 숫자로만 제한
        String userId, // html로 들어오는건 숫자든 문자열이든 모두 자바의 문자열타입으로 받는다

        @NotBlank(message = UserConstants.MSG_INPUT_PW_REQUIRED) // java, validation 레벨 검증
        @Size(min = UserConstants.VALUE_MIN_PASSWORD_SIZE, max=UserConstants.VALUE_MAX_PASSWORD_SIZE, message = UserConstants.MSG_PW_SIZE_REQUIRED)
        String password
) {
    public static CreateUserDto getNewInstance() {
        return new CreateUserDto(null, null);
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