package com.kurai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kurai.constant.ActiveStatus;
import com.kurai.constant.KycStatus;
import com.kurai.dto.ClientRegisterRequest;
import com.kurai.entity.Client;
import com.kurai.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Client register(ClientRegisterRequest req) {
		if (clientRepository.existsByEmail(req.getEmail())) {
			throw new IllegalArgumentException("Email already in use");
		}

		if (clientRepository.existsByPhone(req.getPhone())) {
			throw new IllegalArgumentException("Phone already in use");
		}
		Client client = Client.builder().name(req.getName()).email(req.getEmail()).phone(req.getPhone())
				.passwordHash(passwordEncoder.encode(req.getPassword())).kycStatus(KycStatus.PENDING)
				.activeStatus(ActiveStatus.Y).build();
		return clientRepository.save(client);
	}
}
