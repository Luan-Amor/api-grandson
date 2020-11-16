package com.grandson.apigrandson.controller.comum.dto;

import com.grandson.apigrandson.models.Foto;

import lombok.Getter;

@Getter
public class FotoDto {

	private byte[] data;
	
	public FotoDto() {}
	
	public FotoDto(Foto foto){
		if(foto != null) {			
			this.data = foto.getData();
		}
	}
}
