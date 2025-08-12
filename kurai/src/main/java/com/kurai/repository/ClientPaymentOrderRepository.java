package com.kurai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kurai.entity.ClientPaymentOrder;

@Repository
public interface ClientPaymentOrderRepository extends JpaRepository<ClientPaymentOrder, Long> {
    Optional<ClientPaymentOrder> findByOrderId(String orderId);
}