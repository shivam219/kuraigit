package com.kurai.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kurai.dto.ClientRegisterRequest;
import com.kurai.entity.Client;
import com.kurai.model.ClientLoginRequest;
import com.kurai.security.JsonWebTokenGenerationService;
import com.kurai.service.ClientService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

	@Autowired
	private ClientService clientService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JsonWebTokenGenerationService jsonWebTokenGenerationService;

	@GetMapping("/hi")
	public String hi() {
		return "Hello";
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid ClientRegisterRequest request) {
		Client registered = clientService.register(request);
		return ResponseEntity.ok(Map.of("message", "Client registered successfully", "clientId", registered.getId()));
	}

	@PostMapping("/login")
	public ResponseEntity<?> signIn(@RequestBody @Valid ClientLoginRequest user, HttpServletRequest request,
			HttpServletResponse response) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmailOrNumber(), user.getPassword()));
		boolean isAuthenticated = authentication.isAuthenticated();
		String jwtToken = null;
		if (isAuthenticated) {
			jwtToken = jsonWebTokenGenerationService.generateJWTTokenForUser(authentication, request, response);
		}
		return ResponseEntity.ok(Map.of("token", jwtToken));
	}

}
