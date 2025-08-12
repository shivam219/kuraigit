package com.kurai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kurai.entity.SubscriptionPlanType;
@Repository
public interface SubscriptionPlanTypeRepository extends JpaRepository<SubscriptionPlanType, Long> {
}
