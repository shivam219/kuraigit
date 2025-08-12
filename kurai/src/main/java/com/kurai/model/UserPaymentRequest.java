package com.kurai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentRequest {
	private String emailId;
	private Double amount;	
	private String description;
}
