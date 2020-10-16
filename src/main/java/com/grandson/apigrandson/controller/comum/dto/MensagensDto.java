package com.grandson.apigrandson.controller.comum.dto;

import lombok.Getter;

@Getter
public class MensagensDto {

	private String mensagem;
	
	public MensagensDto(String mensagem){
		this.mensagem = mensagem;
	}
	
}
