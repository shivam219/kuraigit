package com.kurai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientPaymentOrderDto {
	private Long id;
	private String orderId;
	private String clientKey;
	private Long amount;
	private String currency;
	private String company;
	private String description;
}