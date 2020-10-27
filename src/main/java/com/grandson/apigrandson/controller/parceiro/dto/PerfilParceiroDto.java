package com.grandson.apigrandson.controller.parceiro.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.controller.comum.dto.ComentarioDto;
import com.grandson.apigrandson.controller.comum.dto.EnderecoDto;
import com.grandson.apigrandson.controller.comum.dto.FotoDetalheDto;
import com.grandson.apigrandson.models.Parceiro;

import lombok.Getter;

@Getter
public class PerfilParceiroDto {
	
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private String nota;
	private FotoDetalheDto foto = null;
	
	private EnderecoDto endereco;
	
	private LocalDateTime dataInicio;
	private Long quantidadeServico;
	
	private List<ComentarioDto> comentarios = new ArrayList<ComentarioDto>();
	
	public PerfilParceiroDto() {}
	
	public PerfilParceiroDto(Parceiro parceiro) {
		this.id = parceiro.getId();
		this.nome = parceiro.getNome();
		this.cpf = parceiro.getCpf();
		this.email = parceiro.getEmail();
		this.telefone = parceiro.getTelefone();
		this.nota = parceiro.getNota();
		this.quantidadeServico = parceiro.getQuantidadeServicos();
		this.dataInicio = parceiro.getDataInicio();
		
		if(parceiro.getFoto() != null) 
			this.foto = new FotoDetalheDto(parceiro.getFoto());
			
		if(!parceiro.getComentarios().isEmpty())
			this.comentarios.addAll(parceiro.getComentarios().stream().map(ComentarioDto::new).collect(Collectors.toList()));
		
		
		this.endereco = new EnderecoDto(parceiro.getEndereco());

	}
}
