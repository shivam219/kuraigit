package com.kurai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kurai.dto.ClientUPI;
import com.kurai.entity.Client;
import com.kurai.entity.ClientUpiDetails;
import com.kurai.repository.ClientRepository;
import com.kurai.repository.ClientUpiDetailsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientUpiDetailsService {

	@Autowired
	private ClientUpiDetailsRepository upiRepo;
	@Autowired
	private ClientRepository clientRepo;

	public List<ClientUpiDetails> getAllByClientId(Long clientId) {
		Client client = clientRepo.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
		return upiRepo.findByClient(client);
	}

	public ClientUPI getActiveUpi(Long clientId) {
		Client client = clientRepo.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
		return upiRepo.findClientUPI(clientId);
	}

	@Transactional
	public ClientUpiDetails addUpi(Long clientId, ClientUpiDetails upiDetails) {
		Client client = clientRepo.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));

		upiDetails.setClient(client);

		if (upiDetails.isActive()) {
			upiRepo.findByClient(client).forEach(existing -> {
				if (existing.isActive()) {
					existing.setActive(false);
					upiRepo.save(existing);
				}
			});
		}

		return upiRepo.save(upiDetails);
	}
}
