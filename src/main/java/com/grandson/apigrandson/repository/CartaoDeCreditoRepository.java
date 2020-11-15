package com.grandson.apigrandson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grandson.apigrandson.models.CartaoDeCredito;

@Repository
public interface CartaoDeCreditoRepository extends JpaRepository<CartaoDeCredito, Long> {

}
