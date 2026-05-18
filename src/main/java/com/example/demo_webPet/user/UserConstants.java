package com.example.demo_webPet.user;

final class UserConstants {

    static final String MSG_INPUT_ID_REQUIRED = "ID를 입력해주세요" ;

    static final int VALUE_MIN_PASSWORD_SIZE = 8;
    static final int VALUE_MAX_PASSWORD_SIZE = 20;
    static final String MSG_INPUT_PW_REQUIRED = "비밀번호를 입력해주세요";
    static final String MSG_PW_SIZE_REQUIRED = "비밀번호는 " + VALUE_MIN_PASSWORD_SIZE + "~" + VALUE_MAX_PASSWORD_SIZE +"글자여야 합니다";

    private UserConstants(){}
}
