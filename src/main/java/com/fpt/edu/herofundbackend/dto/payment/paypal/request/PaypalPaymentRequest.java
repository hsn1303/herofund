package com.fpt.edu.herofundbackend.dto.payment.paypal.request;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaypalPaymentRequest {

    private long id;
    @NotBlank(message = SystemConstant.Message.NAME_NOT_EMPTY)
    private String name;
    @NotBlank(message = SystemConstant.Message.CLIENT_ID_NOT_EMPTY)
    private String clientId;
    @NotBlank(message = SystemConstant.Message.SECRET_ID_NOT_EMPTY)
    private String secretId;
    @NotBlank(message = SystemConstant.Message.PAYER_ID_NOT_EMPTY)
    private String payerId;

}
