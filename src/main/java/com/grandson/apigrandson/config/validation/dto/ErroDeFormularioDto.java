package com.grandson.apigrandson.config.validation.dto;

import lombok.Getter;

@Getter
public class ErroDeFormularioDto {

	private String campo;
	private String erro;
	
	public ErroDeFormularioDto(String campo, String erro){
		this.campo = campo;
		this.erro = erro;
	}
	
}
