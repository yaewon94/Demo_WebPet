package com.example.demo_webPet.board.RescuedAnimal;

import lombok.Getter;

@Getter
public enum BoardMode {
    ADD("add"), MODIFY("modify"), DELETE("delete");

    private final String value;

    BoardMode(String value){
        this.value = value;
    }
}
