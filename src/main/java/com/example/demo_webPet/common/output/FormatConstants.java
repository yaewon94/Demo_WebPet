package com.example.demo_webPet.common.output;

import java.time.format.DateTimeFormatter;

public final class FormatConstants {

    private FormatConstants(){}

    public static final DateTimeFormatter YEAR_MONTH_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
