package com.kurai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kurai.constant.PlanType;
import com.kurai.dto.ClientSubscriptionDTO;
import com.kurai.entity.ClientSubscription;

@Repository
public interface ClientSubscriptionRepository extends JpaRepository<ClientSubscription, Long> {

//    Optional<ClientSubscription> findByClientIdAndActive(Long clientId);
//    
    @Query("SELECT cs FROM ClientSubscription cs WHERE cs.client.id = :clientId AND cs.active = 'Y' AND cs.remainingScans > 0")
    Optional<ClientSubscription> findActiveWithRemainingScans(@Param("clientId") Long clientId);

    @Query("SELECT cs FROM ClientSubscription cs WHERE cs.client.clientKey = :clientKey AND cs.active = 'Y' AND cs.remainingScans > 0")
    Optional<ClientSubscription> findActiveWithRemainingScansAndClientKey(@Param("clientKey") String clientKey);

    List<ClientSubscription> findByClientId(Long clientId);
    
    @Query("SELECT new com.kurai.dto.ClientSubscriptionDTO(" +
           "cs.id, " +
           "sp.id, " +
           "sp.planName, " +
           "sp.description, " +
           "sp.amount, " +
           "cs.remainingScans, " +
           "cs.planBuyDate) " +
           "FROM ClientSubscription cs " +
           "INNER JOIN SubscriptionPlan sp ON cs.subscriptionPlan.id = sp.id " +
           "WHERE cs.client.id = :clientId AND cs.active = 'Y' AND cs.remainingScans > 0 " +
           "ORDER BY cs.active DESC")
    Optional<ClientSubscriptionDTO> findCurrentActivatedSubscriptionDTO(@Param("clientId") Long clientId);

    @Query("SELECT new com.kurai.dto.ClientSubscriptionDTO(" +
           "cs.id, " +
           "sp.id, " +
           "sp.planName, " +
           "sp.title, " +
           "sp.description, " +
           "sp.amount, " +
           "cs.remainingScans, " +
           "sp.numberOfScans, " +
           "cs.planBuyDate, " +
           "cs.planStartDate, " +
           "cs.active, " +
           "cs.autoActive) " +
           "FROM ClientSubscription cs " +
           "LEFT JOIN SubscriptionPlan sp ON cs.subscriptionPlan.id = sp.id " +
           "WHERE cs.client.id = :clientId AND cs.remainingScans > 0 " +
           "ORDER BY cs.active DESC")
    List<ClientSubscriptionDTO> findCurrentSubscriptionDTO(@Param("clientId") Long clientId);

    boolean existsByClientIdAndSubscriptionPlan_PlanType(Long clientId, PlanType planType);
}