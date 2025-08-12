package com.kurai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurai.entity.SubscriptionPlan;
import com.kurai.service.SubscriptionPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/subscriptions")
@RequiredArgsConstructor
public class AdminSubscriptionController {

	@Autowired
	private SubscriptionPlanService planService;

	@PostMapping("/plans")
	public ResponseEntity<SubscriptionPlan> addPlan(@RequestBody @Valid SubscriptionPlan plan) {
		SubscriptionPlan saved = planService.addPlan(plan);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
}
