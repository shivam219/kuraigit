package com.kurai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kurai.entity.ClientPayment;
import com.kurai.entity.ClientPaymentOrder;
import com.kurai.repository.ClientPaymentOrderRepository;
import com.kurai.repository.ClientPaymentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientPaymentService {

	@Autowired
    private  ClientPaymentRepository paymentRepository;
	@Autowired
    private ClientPaymentOrderRepository orderRepository;

    public ClientPayment recordPayment(String orderId, ClientPayment paymentRequest) {
        ClientPaymentOrder order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        paymentRequest.setOrder(order);
        paymentRequest.setCreatedAt(LocalDateTime.now());
        return paymentRepository.save(paymentRequest);
    }

    public List<ClientPayment> getPaymentsForOrder(String orderId) {
        return paymentRepository.findByOrderOrderId(orderId);
    }

    public Optional<ClientPayment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
}
