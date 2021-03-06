package com.grandson.apigrandson.controller.cliente.dto;

import java.time.format.DateTimeFormatter;

import com.grandson.apigrandson.controller.comum.dto.EnderecoDto;
import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.models.Servico;

import lombok.Getter;

@Getter
public class ServicoDetalhadoParceiroDto {

	private Long idParceiro;
	private Long idServico;
	private String nome;
	private String nota; 
	private double valor;
	private String telefone;
	private double quantidadeHoras;
	private FotoDto foto;
	private String horario;
	private String dia;
	private EnderecoDto endereco;
	private String descricao;
	
	public ServicoDetalhadoParceiroDto() {}
	
	public ServicoDetalhadoParceiroDto(Servico servico) {
		if(servico != null && servico.getParceiro() != null) {			
			this.idParceiro = servico.getParceiro().getId();
			this.nome = servico.getParceiro().getNome();
			this.nota = servico.getParceiro().getNota();
			this.telefone = servico.getParceiro().getTelefone();
			this.foto = new FotoDto(servico.getParceiro().getFoto());		
			this.idServico = servico.getId();
			this.valor = servico.getValor();
			this.quantidadeHoras = servico.getQuantidadeDeHoras();
			this.horario = servico.getHorario().format(DateTimeFormatter.ISO_LOCAL_TIME);
			this.dia = servico.getHorario().format(DateTimeFormatter.ISO_DATE);
			this.descricao = servico.getDescricao();
			
			this.endereco = new EnderecoDto(servico.getEndereco());
		}
		
	}
	
}
