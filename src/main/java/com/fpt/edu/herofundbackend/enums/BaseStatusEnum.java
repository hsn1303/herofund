package com.fpt.edu.herofundbackend.enums;

import lombok.Getter;

@Getter
public enum BaseStatusEnum {

    DISABLE(0, "DISABLE"),
    ENABLE(1, "ENABLE");


    private final int key;
    private final String value;

    BaseStatusEnum(int i, String j) {
        this.key = i;
        this.value = j;
    }
}
