package com.example.demo_webPet.common.output;

import com.example.demo_webPet.common.constants.UrlConstants;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public final class HtmlUtils {

    private HtmlUtils(){}

    // xss 공격 방지
    public static String sanitize(String html){
        return Jsoup.clean(html, UrlConstants.BASE_URI, createSafelist());
    }

    private static Safelist createSafelist() {
        return Safelist.basicWithImages()
                .addTags("figure")
                .addAttributes("figure", "class");
    }
}
