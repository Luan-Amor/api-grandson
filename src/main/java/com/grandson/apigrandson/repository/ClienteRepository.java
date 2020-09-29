package com.grandson.apigrandson.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grandson.apigrandson.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findById(long id);

	public Optional<Cliente> findByEmail(String email);
	
	
}
