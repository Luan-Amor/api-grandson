package com.grandson.apigrandson.controller.comum.dto;

import lombok.Getter;

@Getter
public class FotoListaDto {

	private byte[] data;
	
	public FotoListaDto() {};
	
	public FotoListaDto(byte[] data){
		this.data = data;
	}
	
}
