package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicoDisponiveisDto {

	private Long idServico;
	private Long idCliente;
	private String nome;
	private String nota;
	
	public ServicoDisponiveisDto(){}
	
	public ServicoDisponiveisDto(Servico servico) {
		this.idServico = servico.getId();
		this.idCliente = servico.getCliente().getId();
		this.nome = servico.getCliente().getNome();
		this.nota = servico.getCliente().getNota();
		
	}

	public static List<ServicoDisponiveisDto> converte(List<Servico> servicos) {
		return servicos.stream().map(ServicoDisponiveisDto :: new ).collect(Collectors.toList());
	}
	
	
}
