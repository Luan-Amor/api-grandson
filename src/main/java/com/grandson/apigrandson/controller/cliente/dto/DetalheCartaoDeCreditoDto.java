package com.grandson.apigrandson.controller.cliente.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.grandson.apigrandson.models.CartaoDeCredito;

import lombok.Getter;

@Getter
public class DetalheCartaoDeCreditoDto {

	private Long id;
	
	private String nomeDoCartao;
	private String numeroDoCartao;
	private LocalDate dataDeVencimento;
	
	public DetalheCartaoDeCreditoDto() {}
	
	public DetalheCartaoDeCreditoDto(CartaoDeCredito cartao) {
		this.id = cartao.getId();
		this.nomeDoCartao = cartao.getNomeDoCartao();
		this.numeroDoCartao = cartao.getNumeroDoCartao();
		this.dataDeVencimento = cartao.getDataDeVencimento();
		
	}
}
