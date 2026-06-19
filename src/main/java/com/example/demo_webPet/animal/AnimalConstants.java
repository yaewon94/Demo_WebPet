package com.example.demo_webPet.animal;

public final class AnimalConstants {
    private AnimalConstants(){}

    public static final String INPUT_AGE_REQUIRED = "나이를 입력해주세요";
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 100;
    public static final String ERROR_AGE_RANGE = "나이는 " + MIN_AGE + "살 이상 " + MAX_AGE + "살 이하만 입력 가능합니다";

    public static final String INPUT_SPECIES_REQUIRED = "동물 종류를 선택해주세요";

    public static final String INPUT_GENDER_REQUIRED = "성별을 선택해주세요";

    public static final String INPUT_MISSING_DATE_REQUIRED = "실종날짜를 입력해주세요";
    public static final String ERROR_MISSING_DATE = "실종날짜를 제대로 입력해주세요";
    public static final String INPUT_MISSING_LOCATION_REQUIRED = "실종장소를 선택해주세요";

    public static final String INPUT_RESCUED_DATE_REQUIRED = "구조날짜를 입력해주세요";
    public static final String ERROR_RESCUED_DATE = "구조날짜를 제대로 입력해주세요";
    public static final String INPUT_FOUND_LOCATION_REQUIRED = "구조장소를 선택해주세요";

    public static final String INPUT_IMAGE_REQUIRED = "동물 사진은 필수로 추가해야 합니다";
}
