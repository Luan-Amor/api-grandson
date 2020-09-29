package com.grandson.apigrandson.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grandson.apigrandson.models.Parceiro;

public interface ParceiroRepository extends JpaRepository<Parceiro, Long>{

	public Optional<Parceiro> findByEmail(String email);

}
