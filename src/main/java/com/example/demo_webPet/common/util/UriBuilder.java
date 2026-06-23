package com.example.demo_webPet.common.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

// MVC, REST 방식 상관없이 캡슐화 목적
public final class UriBuilder {

    private UriBuilder(){}

    // Map<String, String> : param key, param value
    public static String getUrl(String path, Map<String, String> params){

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path);
        if (params != null) {
            params.forEach(builder::queryParam);
        }
        return builder.toUriString();
    }
}
