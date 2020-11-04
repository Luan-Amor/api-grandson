package com.grandson.apigrandson.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ContaCorrente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomeBenecifiario;
	private int agencia;
	private int conta;
	private String banco;
	private String tipo;
	
	public ContaCorrente() {}
	
	public ContaCorrente(int agencia, int conta, String banco, String tipo, String nome) {
		this.nomeBenecifiario = nome;
		this.agencia = agencia;
		this.conta = conta;
		this.banco = banco;
		this.tipo = tipo;
	}
}
