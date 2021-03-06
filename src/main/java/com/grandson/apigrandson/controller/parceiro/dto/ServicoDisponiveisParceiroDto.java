package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicoDisponiveisParceiroDto {

	private Long idServico;
	private Long idCliente;
	private String nome;
	private String nota;
	private FotoDto foto;
	
	public ServicoDisponiveisParceiroDto(){}
	
	public ServicoDisponiveisParceiroDto(Servico servico) {
		if(servico.getCliente() != null) {			
			this.idServico = servico.getId();
			this.idCliente = servico.getCliente().getId();
			this.nome = servico.getCliente().getNome();
			this.nota = servico.getCliente().getNota();
			this.foto = new FotoDto(servico.getCliente().getFoto());
		}
	}

	public static Page<ServicoDisponiveisParceiroDto> converte(Page<Servico> servicos) {
		return servicos.map(ServicoDisponiveisParceiroDto :: new);
	}
	
	
}
