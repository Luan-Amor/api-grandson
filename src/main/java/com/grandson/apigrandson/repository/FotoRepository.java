package com.grandson.apigrandson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grandson.apigrandson.models.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{

}
