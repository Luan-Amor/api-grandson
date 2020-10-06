package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.controller.comum.dto.FotoListaDto;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicoDisponiveisDto {

	private Long idServico;
	private Long idParceiro;
	private String nome;
	private String nota;
	private FotoListaDto foto;
	
	public ServicoDisponiveisDto(){}
	
	public ServicoDisponiveisDto(Servico servico) {
		this.idServico = servico.getId();
		this.idParceiro = servico.getParceiro().getId();
		this.nome = servico.getParceiro().getNome();
		this.nota = servico.getParceiro().getNota();
		this.foto = new FotoListaDto(servico.getParceiro().getFoto().getData());
	}

	public static List<ServicoDisponiveisDto> converte(List<Servico> servicos) {
		return servicos.stream().map(ServicoDisponiveisDto :: new ).collect(Collectors.toList());
	}
	
	
}
