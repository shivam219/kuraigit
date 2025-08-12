package com.kurai.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kurai.entity.Client;
import com.kurai.entity.ClientPaymentOrder;
import com.kurai.repository.ClientPaymentOrderRepository;
import com.kurai.repository.ClientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientPaymentOrderService {
	
	@Autowired
    private ClientPaymentOrderRepository orderRepository;
	@Autowired
    private ClientRepository clientRepository;

    public ClientPaymentOrder createOrder(ClientPaymentOrder orderPaymentRequest) {
        Client client = clientRepository.findById(orderPaymentRequest.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        orderPaymentRequest.setOrderId(UUID.randomUUID().toString());
        orderPaymentRequest.setClient(client);
        return orderRepository.save(orderPaymentRequest);
    }

    public Optional<ClientPaymentOrder> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<ClientPaymentOrder> getOrderByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public List<ClientPaymentOrder> getOrdersByClient(Long clientId) {
        return orderRepository.findAll()
                .stream()
                .filter(o -> o.getClient().getId().equals(clientId))
                .toList();
    }
}
