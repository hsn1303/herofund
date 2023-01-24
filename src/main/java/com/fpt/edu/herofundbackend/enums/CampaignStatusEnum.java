package com.fpt.edu.herofundbackend.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum CampaignStatusEnum {

    DISABLE(0, "DISABLE"),
    ENABLE(1, "ENABLE"),
    WAIT(2, "WAIT"),
    URGENT(4, "URGENT"),
    REJECT(3, "REJECT");

    private final int key;
    private final String value;

    CampaignStatusEnum(int i, String j) {
        this.key = i;
        this.value = j;
    }

    public static List<Integer> convertKeyToList(){
        return Arrays.stream(CampaignStatusEnum.values()).map(CampaignStatusEnum::getKey).collect(Collectors.toList());

    }

}
