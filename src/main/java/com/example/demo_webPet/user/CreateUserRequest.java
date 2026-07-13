package com.example.demo_webPet.user;

import jakarta.validation.constraints.*;

// [ record ]
// 디폴트 생성자 x
// 생성후 값 변경 불가능 (안전성 좋음) : Setter 없음
// Getter 어노테이션 필요 없음 : 객체.필드명() 이렇게 가져오면 됨
record CreateUserRequest(
        // valid 어노테이션은 컴파일타임 상수만 가능해서 enum에서 가져오는 문자열은 안됌
        @NotBlank(message = UserConstants.MSG_INPUT_ID_REQUIRED)
        @Size(min = VALUE_MIN_USER_NAME_SIZE, max= VALUE_MAX_USER_NAME_SIZE, message = MSG_USER_NAME_SIZE_REQUIRED)
        @Pattern(regexp = UserConstants.PATTERN_REGEXP_USER_NAME, message = MSG_USER_NAME_PATTERN_CONSTRAINT) // 영어, 숫자로만 제한
        String username, // html로 들어오는건 숫자든 문자열이든 모두 자바의 문자열타입으로 받는다

        @NotBlank(message = UserConstants.MSG_INPUT_PW_REQUIRED) // java, validation 레벨 검증
        @Size(min = VALUE_MIN_PASSWORD_SIZE, max=VALUE_MAX_PASSWORD_SIZE, message = MSG_PW_SIZE_REQUIRED)
        String password,

        @NotNull(message = MSG_USER_TYPE_REQUIRED)
        UserType user_type,

        @NotNull(message = "보호소를 선택해주세요")
        Long shelter_id
) {
    static CreateUserRequest getNewInstance() {
        return new CreateUserRequest(null, null, UserType.NORMAL, -1L);
    }

    private static final int VALUE_MIN_USER_NAME_SIZE = 8;
    private static final int VALUE_MAX_USER_NAME_SIZE = 12;
    private static final String MSG_USER_NAME_SIZE_REQUIRED ="아이디는 " + VALUE_MIN_USER_NAME_SIZE + "~" + VALUE_MAX_USER_NAME_SIZE +"글자여야 합니다";
    private static final String MSG_USER_NAME_PATTERN_CONSTRAINT = "아이디는 영어 소문자,대문자,숫자 조합으로만 만들 수 있습니다";

    private static final int VALUE_MIN_PASSWORD_SIZE = 8;
    private static final int VALUE_MAX_PASSWORD_SIZE = 20;
    private static final String MSG_PW_SIZE_REQUIRED = "비밀번호는 " + VALUE_MIN_PASSWORD_SIZE + "~" + VALUE_MAX_PASSWORD_SIZE +"글자여야 합니다";

    private static final String MSG_USER_TYPE_REQUIRED = "회원타입을 선택해주세요";

//    @AssertTrue(message = ShelterConstants.MSG_SHELTER_INPUT_REQUIRED)
//    public boolean checkValidShelterId() {
//        if (user_type == UserType.SHELTER) {
//            return shelter_id != null;
//        }
//        return true;
//    }
}