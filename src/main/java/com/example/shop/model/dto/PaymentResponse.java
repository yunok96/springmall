package com.example.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    // PayPal 결제 관련
    private String orderId;  // PayPal에서 제공하는 주문 ID
    private String payerId;  // 결제한 사용자의 ID
    private double amount;   // 결제 금액

    private int itemId;
    private int quantity;
}
