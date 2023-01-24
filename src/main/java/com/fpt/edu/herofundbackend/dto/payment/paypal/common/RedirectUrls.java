package com.fpt.edu.herofundbackend.dto.payment.paypal.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedirectUrls {

    public String return_url;
    public String cancel_url;

    public RedirectUrls(String return_url, String cancel_url) {
        this.return_url = return_url;
        this.cancel_url = cancel_url;
    }
}
