package com.fpt.edu.herofundbackend.dto.channel;

import com.fpt.edu.herofundbackend.entity.PaymentChannel;
import lombok.Data;

@Data
public class PaymentChannelDto {

    private long id;
    private String name;

    public PaymentChannelDto(PaymentChannel pc) {
        this.id = pc.getId();
        this.name = pc.getName();
    }
}
