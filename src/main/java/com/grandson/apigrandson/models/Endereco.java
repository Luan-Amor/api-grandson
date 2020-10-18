package com.grandson.apigrandson.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long cep;
	private String endereco;
	private int numero;
	private String complemento;  
	private String cidade;
	private String estado;
	
	public Endereco() {}
	
	public Endereco(Long cep, String endereco, int numero, String complemento,
			String cidade, String estado) {
		this.cep = cep;
		this.endereco = endereco;
		this.numero = numero;
		this.complemento = complemento;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	
}
