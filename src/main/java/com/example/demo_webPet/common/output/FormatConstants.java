package com.example.demo_webPet.common.output;

import java.time.format.DateTimeFormatter;

public final class FormatConstants {

    private FormatConstants(){}

    public static final DateTimeFormatter YEAR_MONTH_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter YMD_HMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
