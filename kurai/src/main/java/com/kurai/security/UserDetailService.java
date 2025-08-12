package com.kurai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kurai.entity.Client;
import com.kurai.model.ClientPrincipal;
import com.kurai.repository.ClientRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String numberOrEmail) throws UsernameNotFoundException {
		Client client;

		if (numberOrEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			client = clientRepository.findByEmail(numberOrEmail)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + numberOrEmail));
		} else if (numberOrEmail.matches("^[0-9]{10}$")) {
			client = clientRepository.findByPhone(numberOrEmail)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with phone: " + numberOrEmail));
		} else {
			throw new UsernameNotFoundException("Invalid login input");
		}

		return new ClientPrincipal(client);
	}

}
