package com.grandson.apigrandson.controller.cliente.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicoDisponiveisParceiroDto;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicosDisponiveisClienteDto {
	
	private Long idServico;
	private Long idCliente;
	private String nome;
	private String nota;
	private FotoDto foto;
	
	public ServicosDisponiveisClienteDto(){}
	
	public ServicosDisponiveisClienteDto(Servico servico) {
		if(servico.getCliente() != null) {			
			this.idServico = servico.getId();
			this.idCliente = servico.getParceiro().getId();
			this.nome = servico.getParceiro().getNome();
			this.nota = servico.getParceiro().getNota();
			this.foto = new FotoDto(servico.getParceiro().getFoto());
		}
	}

	public static Page<ServicosDisponiveisClienteDto> converte(Page<Servico> servicos) {
		return servicos.map(ServicosDisponiveisClienteDto :: new);
	}
}
