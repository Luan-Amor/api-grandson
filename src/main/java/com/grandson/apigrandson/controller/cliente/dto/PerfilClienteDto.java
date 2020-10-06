package com.grandson.apigrandson.controller.cliente.dto;

import java.time.LocalDateTime;

import org.springframework.util.Base64Utils;

import com.grandson.apigrandson.controller.comum.dto.FotoListaDto;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Foto;

import lombok.Getter;

@Getter
public class PerfilClienteDto {

	private Long id;
	private String nome;
	private String telefone;
	private String cpf;
	private String email;
	private String endereco;
	private Long cep;
	private int numero;
	private String complemento;
	private LocalDateTime dataInicio;
	private String nota;
	private FotoListaDto foto;
	
	public PerfilClienteDto(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.dataInicio = cliente.getDataInicio();
		this.nota = cliente.getNota();
		
		if(cliente.getFoto() != null) {
			cliente.getFoto();
			this.foto = new FotoListaDto(cliente.getFoto().getData());
		}
			
		this.endereco = cliente.getEndereco().getEndereco();
		this.cep = cliente.getEndereco().getCep();
		this.complemento = cliente.getEndereco().getComplemento();
		this.numero = cliente.getEndereco().getNumero();
		
	}

}
