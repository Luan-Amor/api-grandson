package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicosAgendadosDto {

	private Long idServico;
	private Long idCliente;
	private String nota;
	private String nome;
	
	public ServicosAgendadosDto(Servico servico) {
		this.idServico = servico.getId();
		this.idCliente = servico.getCliente().getId();
		this.nota = servico.getCliente().getNota();
		this.nome = servico.getCliente().getNome();
	}

	public static List<ServicosAgendadosDto> converte(List<Servico> clientes) {
		return clientes.stream().map(ServicosAgendadosDto::new).collect(Collectors.toList());
	}
	
	
}
