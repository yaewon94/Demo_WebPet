package com.example.demo_webPet.user;

import com.example.demo_webPet.shelter.ShelterConstants;
import jakarta.validation.constraints.*;

// [ record ]
// 디폴트 생성자 x
// 생성후 값 변경 불가능 (안전성 좋음) : Setter 없음
// Getter 어노테이션 필요 없음 : 객체.필드명() 이렇게 가져오면 됨
record CreateUserRequest(
        // valid 어노테이션은 컴파일타임 상수만 가능해서 enum에서 가져오는 문자열은 안됌
        @NotBlank(message = UserConstants.MSG_INPUT_ID_REQUIRED)
        @Size(min = VALUE_MIN_USER_ID_SIZE, max=VALUE_MAX_USER_ID_SIZE, message = MSG_USER_ID_SIZE_REQUIRED)
        @Pattern(regexp = UserConstants.PATTERN_REGEXP_USER_ID, message = MSG_USER_ID_PATTERN_CONSTRAINT) // 영어, 숫자로만 제한
        String userId, // html로 들어오는건 숫자든 문자열이든 모두 자바의 문자열타입으로 받는다

        @NotBlank(message = UserConstants.MSG_INPUT_PW_REQUIRED) // java, validation 레벨 검증
        @Size(min = VALUE_MIN_PASSWORD_SIZE, max=VALUE_MAX_PASSWORD_SIZE, message = MSG_PW_SIZE_REQUIRED)
        String password,

        @NotNull(message = MSG_USER_TYPE_REQUIRED)
        UserType type,

        @NotNull(message = ShelterConstants.MSG_SHELTER_INPUT_REQUIRED)
        Long shelter_id // type == UserType.SHELTER인 경우만 view에 shelter list 보여줄 것
) {
    public static CreateUserRequest getNewInstance() {
        return new CreateUserRequest(null, null, UserType.NORMAL, -1L);
    }

    private static final int VALUE_MIN_USER_ID_SIZE = 8;
    private static final int VALUE_MAX_USER_ID_SIZE = 12;
    private static final String MSG_USER_ID_SIZE_REQUIRED ="아이디는 " + VALUE_MIN_USER_ID_SIZE + "~" + VALUE_MAX_USER_ID_SIZE +"글자여야 합니다";
    private static final String MSG_USER_ID_PATTERN_CONSTRAINT = "아이디는 영어 소문자,대문자,숫자 조합으로만 만들 수 있습니다";

    private static final int VALUE_MIN_PASSWORD_SIZE = 8;
    private static final int VALUE_MAX_PASSWORD_SIZE = 20;
    private static final String MSG_PW_SIZE_REQUIRED = "비밀번호는 " + VALUE_MIN_PASSWORD_SIZE + "~" + VALUE_MAX_PASSWORD_SIZE +"글자여야 합니다";

    private static final String MSG_USER_TYPE_REQUIRED = "회원타입을 선택해주세요";
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