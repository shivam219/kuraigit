package com.kurai.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurai.dto.ClientPaymentOrderDto;
import com.kurai.dto.ClientUPI;
import com.kurai.entity.Client;
import com.kurai.entity.ClientPaymentOrder;
import com.kurai.entity.ClientSubscription;
//import com.kurai.entity.Client;
import com.kurai.model.UpiPaymentRequest;
import com.kurai.model.UpiPaymentResponse;
import com.kurai.repository.ClientRepository;
import com.kurai.repository.ClientSubscriptionRepository;
import com.kurai.repository.ClientUpiDetailsRepository;
import com.kurai.service.ClientPaymentOrderService;
import com.kurai.util.QRCodeUtil;

@RestController
@RequestMapping("/api")
public class ClientOrderAndPaymentController {

	@Autowired
	private ClientPaymentOrderService clientPaymentOrderService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientUpiDetailsRepository clientUpiDetailsRepository;

	@Autowired
	private ClientSubscriptionRepository clientSubscriptionRepository;

	@PostMapping("/orders/create")
	public ResponseEntity<?> createPaymentOrder(@RequestBody ClientPaymentOrder paymentOrder) {
		try {

			System.out.println(paymentOrder);
			String clientKey = paymentOrder.getClientKey();
			if (clientKey == null) {
				return ResponseEntity.badRequest().body(Map.of("message", "Client Key is required"));
			}

			Optional<Client> clientOpt = clientRepository.findByClientKey(clientKey);
			if (clientOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(Map.of("message", "Invalid Client key found. Please upgrade to continue."));
			}

			/* Check subscription */
			Optional<ClientSubscription> optionalSub = clientSubscriptionRepository
					.findActiveWithRemainingScansAndClientKey(clientKey);
			if (optionalSub.isEmpty()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(Map.of("message", "Subscription expired or not found. Please upgrade to continue."));
			}
			/* End Check subscription */

			/* Create and return order */
			Client client = clientOpt.get();
			paymentOrder.setClient(clientOpt.get());
			paymentOrder.setClientId(client.getId());
			ClientPaymentOrder createdOrder = clientPaymentOrderService.createOrder(paymentOrder);

			ClientPaymentOrderDto dto = new ClientPaymentOrderDto();
			dto.setId(createdOrder.getId());
			dto.setOrderId(createdOrder.getOrderId());
			dto.setClientKey(createdOrder.getClientKey());
			dto.setAmount(createdOrder.getAmount());
			dto.setCurrency(createdOrder.getCurrency());
			dto.setCompany(createdOrder.getCompany());
			dto.setDescription(createdOrder.getDescription());
			/* End Create and return order */
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/payment/create")
	public UpiPaymentResponse createPayment(@RequestBody UpiPaymentRequest req) throws Exception {
		Optional<Client> clientOpt = clientRepository.findByClientKey(req.getClientKey());
		if (clientOpt.isEmpty()) {
			throw new IllegalArgumentException("Client not found");
		}
		Client client = clientOpt.get();
		ClientUPI clientUPI = clientUpiDetailsRepository.findClientUPI(client.getId());

		// Track scan: decrement scan count logic here if needed

		String upiLink = String.format("upi://pay?pa=%s&pn=%s&am=%.2f&cu=INR&tn=%s", clientUPI.getUpiId(),
				client.getName().replace(" ", "+"), req.getAmount(), req.getPurpose());

		String qrCodeBase64 = QRCodeUtil.generateQRCodeImage(upiLink, 250, 250);

		return new UpiPaymentResponse(upiLink, qrCodeBase64);
	}

}
