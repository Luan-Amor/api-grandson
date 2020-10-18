package com.grandson.apigrandson.controller.cliente.dto;

import java.time.LocalDate;

import com.grandson.apigrandson.models.CartaoDeCredito;

import lombok.Getter;

@Getter
public class CartaoDeCreditoDto {
	
	private String nomeDoCartao;
	private String numeroDoCartao;
	private long cvv;
	private LocalDate dataDeVencimento;
	
	public CartaoDeCreditoDto(CartaoDeCredito cartao){
		this.nomeDoCartao = cartao.getNomeDoCartao();
		this.numeroDoCartao = cartao.getNumeroDoCartao();
		this.cvv = cartao.getCodigoDeSeguranca();
		this.dataDeVencimento = cartao.getDataDeVencimento();
	}
}
