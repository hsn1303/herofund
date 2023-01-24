package com.fpt.edu.herofundbackend.entity;

import com.fpt.edu.herofundbackend.dto.payment.paypal.request.PaypalPaymentRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "payment_channels")
public class PaymentChannel extends BaseEntity{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "expired")
    private long expired;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "secret_id")
    private String secretId;

    @Column(name = "payer_id")
    private String payerId;

    @Column(name = "token")
    private String token;

    public PaymentChannel(PaypalPaymentRequest request) {
        this.name = request.getName();
        this.clientId = request.getClientId();
        this.payerId = request.getPayerId();
        this.secretId = request.getSecretId();
    }
}
