package com.fpt.edu.herofundbackend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "transactions")
@Builder
public class Transaction {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "amount")
    private long amount;

    @Column(name = "sending_time")
    private LocalDateTime sendingTime;

    @Column(name = "campaign_id")
    private long campaignId;

    @Column(name = "payment_channel")
    private long paymentChannel;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "paypal_transaction_id")
    private String paypalTransactionId;

    @Column(name = "order_id")
    private String orderId;

}
