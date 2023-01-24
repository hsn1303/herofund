package com.fpt.edu.herofundbackend.dto.payment.paypal.response;

import com.fpt.edu.herofundbackend.dto.payment.paypal.common.PurchaseUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaypalExecuteResponse {

    private String id;
    private String status;
    private ArrayList<PurchaseUnit> purchase_units;
    private String create_time;
    private String update_time;
}
