package com.kurai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kurai.dto.ClientUPI;
import com.kurai.entity.Client;
import com.kurai.entity.ClientUpiDetails;

@Repository
public interface ClientUpiDetailsRepository extends JpaRepository<ClientUpiDetails, Long> {

	List<ClientUpiDetails> findByClient(Client client);

	Optional<ClientUpiDetails> findByClientAndIsActiveTrue(Client client);

	boolean existsByUpiId(String upiId);
	
	@Query("SELECT  new com.kurai.dto.ClientUPI( ds.client.id , upiId  )  FROM ClientUpiDetails ds "
			+ " WHERE ds.client.id = :clientId")
	public ClientUPI findClientUPI(@Param("clientId") Long clientId);

}
