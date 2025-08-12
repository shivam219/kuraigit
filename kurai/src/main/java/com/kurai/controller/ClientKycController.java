package com.kurai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurai.dto.ClientUPI;
import com.kurai.entity.ClientUpiDetails;
import com.kurai.model.ClientPrincipal;
import com.kurai.security.LoggedInUser;
import com.kurai.service.ClientUpiDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/kyc/client-upi")
@RequiredArgsConstructor
public class ClientKycController {

	@Autowired
	private ClientUpiDetailsService upiService;

//	@GetMapping("/{clientId}")
//	public ResponseEntity<List<ClientUpiDetails>> getAllUpi(@PathVariable Long clientId) {
//		return ResponseEntity.ok(upiService.getAllByClientId(clientId));
//	}

	@GetMapping("/connect-upi")
	public ClientUPI getActive(@LoggedInUser ClientPrincipal user) {
		return upiService.getActiveUpi(user.getClientId());
	}

	@PostMapping("/connect-upi")
	public ResponseEntity<ClientUpiDetails> addUpi(@LoggedInUser ClientPrincipal user,
			@RequestBody ClientUpiDetails details) {
		return ResponseEntity.ok(upiService.addUpi(user.getClientId(), details));
	}
}
