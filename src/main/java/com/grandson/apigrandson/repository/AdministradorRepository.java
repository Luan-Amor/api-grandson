package com.grandson.apigrandson.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grandson.apigrandson.models.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

	public Optional<Administrador> findByEmail(String email);
	
}
