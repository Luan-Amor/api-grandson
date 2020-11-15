package com.grandson.apigrandson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grandson.apigrandson.models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
