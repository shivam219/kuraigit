package com.kurai.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.kurai.model.ClientPrincipal;
/* Copyright (c) 2023-2024 Enhanced software solution private limited,
 * All rights reserved
 * 
 * @author          
 * Shivam Choudhary  
 * 25-Jan-2024
 */

@Component("auditorAware")
class AuditorAwareImp implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof ClientPrincipal) {
			ClientPrincipal userPrincipal = (ClientPrincipal) principal;
			return Optional.of(userPrincipal.getUsername());
		}
		return Optional.empty();
	}
}