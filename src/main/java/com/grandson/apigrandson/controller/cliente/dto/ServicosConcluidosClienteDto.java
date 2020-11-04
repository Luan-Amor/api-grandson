package com.grandson.apigrandson.controller.cliente.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicoDisponiveisParceiroDto;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicosConcluidosClienteDto {

	private Long idServico;
	private Long idParceiro;
	private String nome;
	private String nota;
	private FotoDto foto;
	
	public ServicosConcluidosClienteDto(){}
	
	public ServicosConcluidosClienteDto(Servico servico) {
		if(servico.getCliente() != null) {			
			this.idServico = servico.getId();
			this.idParceiro = servico.getParceiro().getId();
			this.nome = servico.getParceiro().getNome();
			this.nota = servico.getParceiro().getNota();
			this.foto = new FotoDto(servico.getParceiro().getFoto());
		}
	}

	public static List<ServicosConcluidosClienteDto> converte(List<Servico> servicos) {
		return servicos.stream().map(ServicosConcluidosClienteDto :: new).collect(Collectors.toList());
	}
	
}
