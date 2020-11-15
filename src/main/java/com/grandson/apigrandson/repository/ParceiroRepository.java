package com.grandson.apigrandson.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grandson.apigrandson.models.Parceiro;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long>{

	public Optional<Parceiro> findByEmail(String email);
	
	public Optional<Parceiro> findByCpf(String cpf);

}
