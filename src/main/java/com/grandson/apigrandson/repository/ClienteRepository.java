package com.grandson.apigrandson.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grandson.apigrandson.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findById(long id);

	public Optional<Cliente> findByEmail(String email);
	
	public Optional<Cliente> findByCpf(String cpf);
	
	
}
