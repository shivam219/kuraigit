package com.kurai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kurai.entity.SubscriptionPlan;
import com.kurai.repository.SubscriptionPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

	@Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlan addPlan(SubscriptionPlan plan) {
        return subscriptionPlanRepository.save(plan);
    }
}
