package com.grandson.apigrandson.controller.comum.dto;

import com.grandson.apigrandson.models.Foto;

import lombok.Getter;

@Getter
public class FotoDetalheDto {

	private String nome;
	private String type;
	private byte[] data;
	
	public FotoDetalheDto(Foto foto){
		this.nome = foto.getNome();
		this.type = foto.getType();
		this.data = foto.getData();
	}
	
}
