package com.fpt.edu.herofundbackend.dto.payment.paypal.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    public String name;
    public String description;
    public String quantity;
    public UnitAmount unit_amount;
}
