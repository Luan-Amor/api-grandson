package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicosAgendadosDto {

	private Long idServico;
	private Long idCliente;
	private String nota;
	private String nome;
	private FotoDto foto;
	
	
	public ServicosAgendadosDto(Servico servico) {
		
		this.idServico = servico.getId();
		this.idCliente = servico.getCliente().getId();
		this.nota = servico.getCliente().getNota();
		this.nome = servico.getCliente().getNome();
		this.foto = new FotoDto(servico.getCliente().getFoto());
	}

	public static Page<ServicosAgendadosDto> converte(Page<Servico> clientes) {
		return clientes.map(ServicosAgendadosDto::new);
	}
	
	
}
