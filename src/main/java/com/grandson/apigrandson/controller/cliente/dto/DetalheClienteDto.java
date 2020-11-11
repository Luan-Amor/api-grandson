package com.grandson.apigrandson.controller.cliente.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grandson.apigrandson.controller.comum.dto.ComentarioDto;
import com.grandson.apigrandson.controller.comum.dto.EnderecoDto;
import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.controller.parceiro.dto.ComentariosParceiroDto;
import com.grandson.apigrandson.models.Cliente;

import lombok.Getter;

@Getter
public class DetalheClienteDto {

	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private LocalDateTime dataInicio;
	private String nota;
	private FotoDto foto;
	private EnderecoDto endereco;
	private Long quantidadeServico;
	private List<ComentarioDto> comentarios = new ArrayList<ComentarioDto>();
	
	public DetalheClienteDto() {}
	
	public DetalheClienteDto(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataInicio = cliente.getDataInicio();
		this.nota = cliente.getNota();
		this.foto = new FotoDto(cliente.getFoto());
		this.endereco = new EnderecoDto(cliente.getEndereco());
		this.quantidadeServico = cliente.getQuantidadeServicos();
		this.comentarios = cliente.getComentarios().stream().map(ComentarioDto::new)
				.filter(o -> o.getTexto() != null).collect(Collectors.toList());
	}
	
	
}
