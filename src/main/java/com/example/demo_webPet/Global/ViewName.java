package com.example.demo_webPet.Global;

import lombok.Getter;

@Getter
public enum ViewName {
    SIGNUP("signup");

    private final String name;

    ViewName(String view) {
        this.name = view;
    }
}