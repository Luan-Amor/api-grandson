package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.models.Bancos;

import lombok.Getter;

@Getter
public class BancosDto {

	private String codigo;
	private String banco;
	
	public BancosDto(Bancos banco) {
		this.banco = banco.getBanco();
		this.codigo = banco.getCodigo();
	}

	public static List<BancosDto> converte(List<Bancos> bancos) {
		return bancos.stream().map(BancosDto::new).collect(Collectors.toList());
	} 
	
	
}
