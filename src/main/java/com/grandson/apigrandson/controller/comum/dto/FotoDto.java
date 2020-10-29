package com.grandson.apigrandson.controller.comum.dto;

import com.grandson.apigrandson.models.Foto;

import lombok.Getter;

@Getter
public class FotoDto {


	private byte[] data;
	
	public FotoDto(Foto foto){
		this.data = foto.getData();
	}
	
}
