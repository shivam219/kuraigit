package com.kurai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kurai.constant.PlanType;
import com.kurai.entity.SubscriptionPlan;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
	 List<SubscriptionPlan> findByPlanTypeNot(PlanType planType);
}
