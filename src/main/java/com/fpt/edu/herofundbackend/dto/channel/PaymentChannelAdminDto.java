package com.fpt.edu.herofundbackend.dto.channel;

import com.fpt.edu.herofundbackend.entity.PaymentChannel;
import lombok.Data;

@Data
public class PaymentChannelAdminDto {

    private long id;
    private String name;
    private String clientId;
    private String secretId;
    private int status;
    private String payerId;

    public PaymentChannelAdminDto(PaymentChannel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
        this.clientId = channel.getClientId();
        this.payerId = channel.getPayerId();
        this.secretId = channel.getSecretId();
        this.status = channel.getStatus();
    }
}
