package com.grandson.apigrandson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grandson.apigrandson.models.Bancos;

@Repository
public interface BancosRepository extends JpaRepository<Bancos, Long>{

}
