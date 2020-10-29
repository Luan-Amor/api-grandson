package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicoDisponiveisDto {

	private Long idServico;
	private Long idParceiro;
	private String nome;
	private String nota;
	private FotoDto foto;
	
	public ServicoDisponiveisDto(){}
	
	public ServicoDisponiveisDto(Servico servico) {
		this.idServico = servico.getId();
		this.idParceiro = servico.getParceiro().getId();
		this.nome = servico.getParceiro().getNome();
		this.nota = servico.getParceiro().getNota();
		
		if(servico.getParceiro().getFoto() != null)
			this.foto = new FotoDto(servico.getParceiro().getFoto());
	}

	public static List<ServicoDisponiveisDto> converte(List<Servico> servicos) {
		return servicos.stream().map(ServicoDisponiveisDto :: new ).collect(Collectors.toList());
	}
	
	
}
