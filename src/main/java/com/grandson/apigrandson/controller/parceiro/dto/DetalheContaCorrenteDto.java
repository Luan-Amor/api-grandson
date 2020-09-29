package com.grandson.apigrandson.controller.parceiro.dto;

import com.grandson.apigrandson.models.ContaCorrente;

import lombok.Getter;

@Getter
public class DetalheContaCorrenteDto {

	private Long id;
	private int agencia;
	private int conta;
	private String banco;
	private String tipo;

	public DetalheContaCorrenteDto(ContaCorrente contaCorrente) {
		this.id = contaCorrente.getId();
		this.agencia = contaCorrente.getAgencia();
		this.conta = contaCorrente.getConta();
		this.banco = contaCorrente.getBanco();
		this.tipo = contaCorrente.getTipo();
	}
	
	
}
