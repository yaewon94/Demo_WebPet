package com.example.demo_webPet.user;

final class UserConstants {

    static final int VALUE_MIN_USER_ID_SIZE = 8;
    static final int VALUE_MAX_USER_ID_SIZE = 12;
    static final String PATTERN_REGEXP_USER_ID = "^[a-zA-Z0-9]+$";
    static final String MSG_INPUT_ID_REQUIRED = "ID를 입력해주세요" ;
    static final String MSG_USER_ID_SIZE_REQUIRED ="아이디는 " + VALUE_MIN_USER_ID_SIZE + "~" + VALUE_MAX_USER_ID_SIZE +"글자여야 합니다";
    static final String MSG_USER_ID_PATTERN_CONSTRAINT = "아이디는 영어 소문자,대문자,숫자 조합으로만 만들 수 있습니다";

    static final int VALUE_MIN_PASSWORD_SIZE = 8;
    static final int VALUE_MAX_PASSWORD_SIZE = 20;
    static final String MSG_INPUT_PW_REQUIRED = "비밀번호를 입력해주세요";
    static final String MSG_PW_SIZE_REQUIRED = "비밀번호는 " + VALUE_MIN_PASSWORD_SIZE + "~" + VALUE_MAX_PASSWORD_SIZE +"글자여야 합니다";

    private UserConstants(){}
}
