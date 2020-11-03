package com.grandson.apigrandson.controller.comum.dto;

import lombok.Getter;

@Getter
public class MensagensDto {

	private Long object;
	private String mensagem;
	
	public MensagensDto(String mensagem){
		this.mensagem = mensagem;
	}
	
	public MensagensDto(String mensagem, Long object){
		this.object = object;
		this.mensagem = mensagem;
	}
}
