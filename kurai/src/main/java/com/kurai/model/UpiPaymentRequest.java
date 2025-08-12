package com.kurai.model;

import lombok.Data;

@Data
public class UpiPaymentRequest {
	private Long clientId;
	private Double amount;
	private String purpose;
	private String clientKey;
	private String orderId;
	private String paymentType; // UPI, CARD, etc.
}
