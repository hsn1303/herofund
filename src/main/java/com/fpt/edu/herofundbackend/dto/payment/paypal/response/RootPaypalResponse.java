package com.fpt.edu.herofundbackend.dto.payment.paypal.response;

import com.fpt.edu.herofundbackend.dto.payment.paypal.common.Link;
import com.fpt.edu.herofundbackend.dto.payment.paypal.common.Payer;
import com.fpt.edu.herofundbackend.dto.payment.paypal.common.PurchaseUnit;
import com.fpt.edu.herofundbackend.dto.payment.paypal.common.TransactionPaypal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RootPaypalResponse {

    private String id;
    private String intent;
    private String status;
    public ArrayList<PurchaseUnit> purchase_units;
    private String create_time;
    private ArrayList<Link> links;
}
