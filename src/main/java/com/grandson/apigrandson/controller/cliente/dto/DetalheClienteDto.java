package com.grandson.apigrandson.controller.cliente.dto;

import java.time.LocalDateTime;

import com.grandson.apigrandson.models.Cliente;

import lombok.Getter;

@Getter
public class DetalheClienteDto {

	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private LocalDateTime dataInicio;
	private String nota;
	
	public DetalheClienteDto() {}
	
	public DetalheClienteDto(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataInicio = cliente.getDataInicio();
		this.nota = cliente.getNota();
	}
	
	
}
