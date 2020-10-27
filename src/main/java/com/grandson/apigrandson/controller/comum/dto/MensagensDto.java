package com.grandson.apigrandson.controller.comum.dto;

import lombok.Getter;

@Getter
public class MensagensDto {

	private Object object;
	private String mensagem;
	
	public MensagensDto(String mensagem){
		this.mensagem = mensagem;
	}
	
	public MensagensDto(String mensagem, Object object){
		this.object = object;
		this.mensagem = mensagem;
	}
}
