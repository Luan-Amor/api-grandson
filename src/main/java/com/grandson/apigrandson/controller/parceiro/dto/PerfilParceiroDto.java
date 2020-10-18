package com.grandson.apigrandson.controller.parceiro.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.grandson.apigrandson.controller.comum.dto.EnderecoDto;
import com.grandson.apigrandson.models.Comentario;
import com.grandson.apigrandson.models.Foto;
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
	private Foto foto;
	
	private EnderecoDto endereco;
	
	private LocalDateTime dataInicio;
	private Long quantidadeServico;
	
	private List<Comentario> comentarios;
	
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
		this.comentarios = parceiro.getComentarios();
		
		if(parceiro.getFoto() != null) 
			this.foto = parceiro.getFoto();
		
		this.endereco = new EnderecoDto(parceiro.getEndereco());

	}
}
