package com.grandson.apigrandson.controller.parceiro.dto;

import java.time.format.DateTimeFormatter;

import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicoDetalhadoClienteDto {

	private Long idServico;
	private String nome;
	private String nota; 
	private double valor;
	private String telefone;
	private int quantidadeHoras;
	private String horario;
	private String dia;
	
	public ServicoDetalhadoClienteDto() {}
	
	public ServicoDetalhadoClienteDto(Servico servico) {
		this.nome = servico.getCliente().getNome();
		this.nota = servico.getCliente().getNota();
		this.telefone = servico.getCliente().getTelefone();
		
		this.idServico = servico.getId();
		this.valor = servico.getValor();
		this.quantidadeHoras = servico.getQuantidadeDeHoras();
		this.horario = servico.getHorario().format(DateTimeFormatter.ISO_LOCAL_TIME);
		this.dia = servico.getHorario().format(DateTimeFormatter.ISO_DATE);
		
	}
	
}
