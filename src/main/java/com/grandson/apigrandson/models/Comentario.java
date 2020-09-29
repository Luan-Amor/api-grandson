package com.grandson.apigrandson.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comentarioCliente;
	private String comentarioParceiro;
	
	@ManyToOne
	private Parceiro parceiro;
	
	@ManyToOne
	private Cliente cliente;
}
