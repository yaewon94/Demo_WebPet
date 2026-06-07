package com.example.demo_webPet.common.util;

import java.util.Arrays;

public final class EnumUtils {

    private EnumUtils(){}

    public static <T extends Enum<T>> T parse(Class<T> enumClass, String value
    ) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(value + " : TYPE CASTING ERROR"));
    }
}
