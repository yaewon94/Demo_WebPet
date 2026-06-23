package com.example.demo_webPet.common.constants;

public final class UrlConstants {

    public static final String URL_SIGNUP = "/user/signup";
    public static final String URL_LOGIN = "/user/login";
    public static final String URL_LOGOUT = "/user/logout";

    public static final String URL_BOARD_MISSING_ANIMAL_ROOT = "/board/missingAnimal";
    public static final String URL_BOARD_MISSING_ANIMAL_LIST = URL_BOARD_MISSING_ANIMAL_ROOT + "/list";
    public static final String URL_BOARD_MISSING_ANIMAL_ADD = URL_BOARD_MISSING_ANIMAL_ROOT + "/add";
    public static final String URL_BOARD_MISSING_ANIMAL_DETAIL = URL_BOARD_MISSING_ANIMAL_ROOT + "/detail";
    public static final String URL_BOARD_MISSING_ANIMAL_MODIFY = URL_BOARD_MISSING_ANIMAL_ROOT + "/modify";
    public static final String URL_BOARD_MISSING_ANIMAL_DELETE = URL_BOARD_MISSING_ANIMAL_ROOT + "/delete";

    public static final String URL_BOARD_RESCUED_ANIMAL_ROOT = "/board/rescuedAnimal";
    public static final String URL_BOARD_RESCUED_ANIMAL_LIST = URL_BOARD_RESCUED_ANIMAL_ROOT + "/list";
    public static final String URL_BOARD_RESCUED_ANIMAL_ADD = URL_BOARD_RESCUED_ANIMAL_ROOT + "/add";

    private UrlConstants(){}
}
