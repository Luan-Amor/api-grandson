package com.grandson.apigrandson.controller.comum.dto;

import lombok.Getter;

@Getter
public class TokenDto {
	
	private String nome;
	private String token; 
	private String header;
	
	public TokenDto(String token, String header, String nome) {
		this.nome = nome;
		this.token = token;
		this.header = header;
	}



}
