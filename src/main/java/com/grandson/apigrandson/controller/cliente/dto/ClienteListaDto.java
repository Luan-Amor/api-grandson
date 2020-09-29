package com.grandson.apigrandson.controller.cliente.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.models.Cliente;

import lombok.Getter;

@Getter
public class ClienteListaDto {

	private long id;
	private String nome;
	private String nota;
	
	public ClienteListaDto() {}
	
	public ClienteListaDto(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.nota = cliente.getNota();
	}

	public static List<ClienteListaDto> converte(List<Cliente> clientes) {
		return clientes.stream().map(ClienteListaDto::new).collect(Collectors.toList());
	}
}
