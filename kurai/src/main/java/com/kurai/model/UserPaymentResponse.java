package com.kurai.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserPaymentResponse {
	private String orderId;
	private Double amount;
	private String currency;
	private String razorpayKey;
	private String company;
	private String description;
}