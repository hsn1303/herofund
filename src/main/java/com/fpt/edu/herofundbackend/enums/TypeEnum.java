package com.fpt.edu.herofundbackend.enums;

import lombok.Getter;

@Getter
public enum TypeEnum {
    ARTICLE(1),
    COMMENT(2);
    private final long value;
    TypeEnum(long i) {
        this.value = i;
    }
}
