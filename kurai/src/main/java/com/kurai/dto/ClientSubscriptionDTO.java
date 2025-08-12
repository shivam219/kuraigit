package com.kurai.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientSubscriptionDTO {
	private Long id;
	private Long planId;
	private String planName;	
	private String title;
	private String description;
	private Double amount;
	private Integer remainingScans;
	private Integer numberOfScans;
	private LocalDateTime planBuyDate;
	private LocalDateTime planStartDate;
	private String active;
	private String autoActive;
	
	public ClientSubscriptionDTO(Long id, Long planId, String planName, String description, Double amount,
			Integer remainingScans, LocalDateTime planBuyDate) {
		super();
		this.id = id;
		this.planId = planId;
		this.planName = planName;
		this.description = description;
		this.amount = amount;
		this.remainingScans = remainingScans;
		this.planBuyDate = planBuyDate;
	}
	
	
	
	

}
