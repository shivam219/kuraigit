package com.kurai.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kurai.constant.PlanType;
import com.kurai.entity.Client;
import com.kurai.entity.ClientSubscription;
import com.kurai.entity.SubscriptionPlan;
import com.kurai.exception.SubscriptionException;
import com.kurai.repository.ClientRepository;
import com.kurai.repository.ClientSubscriptionRepository;
import com.kurai.repository.SubscriptionPlanRepository;

@Service
public class ClientSubscriptionService {

	@Autowired
	private ClientSubscriptionRepository clientSubscriptionRepository;
	@Autowired
	private SubscriptionPlanRepository planRepository;
	@Autowired
	private ClientRepository clientRepository;

	public ClientSubscription activateSubscription(Long clientId, Long clientPlanId) {
		ClientSubscription clientPlan = clientSubscriptionRepository.findById(clientPlanId)
				.orElseThrow(() -> new RuntimeException("Subscription plan not found"));
		clientPlan.setActive("Y");
		clientPlan.setPlanBuyDate(LocalDateTime.now());
		clientPlan.setPlanStartDate(LocalDateTime.now());
		return clientSubscriptionRepository.save(clientPlan);
	}

	public ClientSubscription turnOnAutoActiveSubscription(Long clientId, Long clientPlanId) {
		ClientSubscription clientPlan = clientSubscriptionRepository.findById(clientPlanId)
				.orElseThrow(() -> new RuntimeException("Subscription plan not found"));
		clientPlan.setAutoActive("Y");
		clientPlan.setUpdatedAt(LocalDateTime.now());
		return clientSubscriptionRepository.save(clientPlan);
	}

	public ClientSubscription buySubscription(Long clientId, Long planId) {
		SubscriptionPlan plan = planRepository.findById(planId)
				.orElseThrow(() -> new RuntimeException("Subscription plan not found"));
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new RuntimeException("Subscription plan not found"));
		if (plan.getPlanType() == PlanType.FREE) {
			boolean alreadySubscribed = clientSubscriptionRepository
					.existsByClientIdAndSubscriptionPlan_PlanType(clientId, PlanType.FREE);
			if (alreadySubscribed) {
				throw new SubscriptionException("You have already claimed the FREE plan.");
			}
		}
		ClientSubscription subscription = new ClientSubscription();
		subscription.setClient(client);
		subscription.setSubscriptionPlan(plan);
		subscription.setRemainingScans(plan.getNumberOfScans());
		subscription.setTotalScans(plan.getNumberOfScans());
		subscription.setPlanBuyDate(LocalDateTime.now());
		return clientSubscriptionRepository.save(subscription);
	}

}
