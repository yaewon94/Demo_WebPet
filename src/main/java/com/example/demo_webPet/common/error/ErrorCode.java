package com.example.demo_webPet.common.error;

import com.example.demo_webPet.common.constants.CommonValidConstants;
import com.example.demo_webPet.common.upload.FileConstants;
import lombok.Getter;

@Getter
public enum ErrorCode{

    ERROR_USER_NAME_IS_NOT_EXIST("E_Auth1", "존재하지 않는 아이디 입니다"),
    ERROR_USER_PASSWORD_MISMATCH("E_Auth2", "비밀번호가 일치하지 않습니다"),
    ERROR_USER_IS_NOT_EXIST("E_Auth3", "존재하지 않는 회원입니다"),
    ERROR_NOT_SHELTER_USER("E_Auth4", "보호소 회원만 이용 가능합니다"),
    ERROR_NOT_NORMAL_USER("E_Auth5", "일반회원만 이용 가능합니다"),
    ERROR_ALREADY_LOGGED_IN("E_Auth6", "이미 로그인 상태입니다"),

    ERROR_DUPLICATED_USER_NAME("E_User1", "이미 존재하는 아이디 입니다"),

    ERROR_BOARD_ACCESS_DENIED("E_Board1", "해당 게시물에 접근할 수 없습니다"),
    BOARD_NOT_EXIST("E_Board2", "존재하지 않는 게시물입니다"),
    BOARD_COMMENT_NOT_EXIST("E_Board3", "존재하지 않는 댓글입니다"),
    BOARD_COMMENT_PASSWORD_MISMATCH("E_Board4", "비밀번호가 일치하지 않습니다"),
    BOARD_COMMENT_USERNAME_REQUIRED("E_Board5", "작성자 이름을 입력하세요"),
    BOARD_COMMENT_USERNAME_NOT_VALID("E_Board6", "작성자 이름은 " + CommonValidConstants.MAX_SIZE_BOARD_COMMENT_GUEST_USERNAME + "글자 이하만 가능합니다"),
    BOARD_COMMENT_PASSWORD_REQUIRED("E_Board7", "비밀번호를 입력하세요"),
    BOARD_COMMENT_PASSWORD_NOT_VALID("E_Board8", CommonValidConstants.MAX_SIZE_BOARD_COMMENT_GUEST_PASSWORD + "글자 이하만 가능합니다"),
    BOARD_COMMENT_ACCESS_DENIED("E_Board9", "접근 권한이 없습니다"),

    ERROR_FILE_REQUIRED("E_File1", "파일을 업로드 해주세요"),
    ERROR_ONLY_IMAGE_FILE_REQUIRED("E_File2", "이미지 파일만 업로드 가능합니다"),
    ERROR_IMAGE_FILE_SIZE("E_File3", FileConstants.MAX_FILE_SIZE_GB + "GB 이하의 이미지 파일만 업로드 가능합니다");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}