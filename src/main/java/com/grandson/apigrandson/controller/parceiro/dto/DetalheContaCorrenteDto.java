package com.grandson.apigrandson.controller.parceiro.dto;

import com.grandson.apigrandson.models.ContaCorrente;

import lombok.Getter;

@Getter
public class DetalheContaCorrenteDto {

	private Long id;
	private String nomeBeneficiario; 
	private int agencia;
	private int conta;
	private String banco;
	private String tipo;
	private double valor;

	public DetalheContaCorrenteDto(ContaCorrente contaCorrente, double saldo) {
		this.id = contaCorrente.getId();
		this.nomeBeneficiario = contaCorrente.getNomeBenecifiario();
		this.agencia = contaCorrente.getAgencia();
		this.conta = contaCorrente.getConta();
		this.banco = contaCorrente.getBanco();
		this.tipo = contaCorrente.getTipo();
		this.valor = saldo;
	}
	
	
}
