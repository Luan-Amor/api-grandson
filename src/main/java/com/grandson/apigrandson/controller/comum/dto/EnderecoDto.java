package com.grandson.apigrandson.controller.comum.dto;

import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class EnderecoDto {

	private Long cep;
	private String endereco;
	private int numero;
	private String complemento;  
	private String cidade;
	private String estado;
	
	public EnderecoDto(Endereco endereco) {
		if(endereco == null) {
			return;
		}
		this.cep = endereco.getCep();
		this.endereco = endereco.getEndereco();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
		this.cidade = endereco.getCidade();
		this.estado = endereco.getEstado();
		
	}
	
}
