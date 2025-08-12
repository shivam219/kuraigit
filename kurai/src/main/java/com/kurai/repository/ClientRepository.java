package com.kurai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kurai.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
//    Optional<Client> findByEmail(String email);
	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

//	Client findByEmail(String email);
//
//	Client findByPhone(String phone);
	
	Optional<Client> findByEmail(String email);

	Optional<Client> findByPhone(String phone);
	
	Optional<Client> findByClientKey(String clientKey);
	
}
