package com.grandson.apigrandson.controller.comum.dto;

import lombok.Getter;

@Getter
public class TokenDto {
	
	private String token; 
	private String header;
	
	public TokenDto(String token, String header) {
		this.token = token;
		this.header = header;
	}



}
