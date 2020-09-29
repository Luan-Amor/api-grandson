package com.grandson.apigrandson.controller.cliente.dto;

import java.time.LocalDateTime;

import com.grandson.apigrandson.models.Cliente;

import lombok.Getter;

@Getter
public class PerfilClienteDto {

	private Long id;
	private String nome;
	private String telefone;
	private String cpf;
	private String email;
	private String endereco;
	private Long cep;
	private int numero;
	private String complemento;
	private LocalDateTime dataInicio;
	private String nota;
	
	public PerfilClienteDto(Cliente cliente) {
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataInicio = cliente.getDataInicio();
		this.nota = cliente.getNota();
		
		this.endereco = cliente.getEndereco().getEndereco();
		this.cep = cliente.getEndereco().getCep();
		this.complemento = cliente.getEndereco().getComplemento();
		this.numero = cliente.getEndereco().getNumero();
		
	}

}
