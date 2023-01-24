package com.fpt.edu.herofundbackend.dto.payment.paypal.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUnit {

    private ArrayList<Item> items;
    private Amount amount;
    private Payments payments;
}
