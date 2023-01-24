package com.fpt.edu.herofundbackend.dto.payment.paypal.request;

import com.fpt.edu.herofundbackend.dto.payment.PaymentOder;
import com.fpt.edu.herofundbackend.dto.payment.paypal.common.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RootPaypalRequest {

    public String intent;
    public ArrayList<PurchaseUnit> purchase_units;
    public RedirectUrls application_context;

    public RootPaypalRequest(PaymentOder paymentOder, String returnUrl, String requestUrl, String campaignName) {
        long amountOrder = paymentOder.getAmount();
        this.intent = "CAPTURE";
        this.application_context = new RedirectUrls(returnUrl, requestUrl);
        Breakdown breakdown = new Breakdown();

        ItemTotal itemTotal = new ItemTotal("USD", String.valueOf(amountOrder));
        breakdown.setItem_total(itemTotal);
        Amount amount = Amount.builder()
                .breakdown(breakdown)
                .value(String.valueOf(amountOrder))
                .currency_code("USD").build();

        Item item = Item.builder()
                .name(campaignName)
                .description( paymentOder.getSenderName() + ": " + paymentOder.getMessage())
                .quantity("1")
                .unit_amount(new UnitAmount("USD", String.valueOf(amountOrder)))
                .build();
        PurchaseUnit purchaseUnit = new PurchaseUnit();
        purchaseUnit.setAmount(amount);
        purchaseUnit.setItems(new ArrayList<>(Collections.singleton(item)));
        this.purchase_units = new ArrayList<>(Collections.singleton(purchaseUnit));
    }
}
