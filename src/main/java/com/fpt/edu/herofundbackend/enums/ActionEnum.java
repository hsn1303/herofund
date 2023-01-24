package com.fpt.edu.herofundbackend.enums;

import lombok.Getter;

@Getter
public enum ActionEnum {

    SAVE(0, "SAVE"),
    UPDATE(1, "UPDATE");

    private final int key;
    private final String value;

    ActionEnum(int i, String j) {
        this.key = i;
        this.value = j;
    }
}
