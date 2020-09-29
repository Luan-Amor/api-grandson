package com.grandson.apigrandson.controller.parceiro.form;

import java.time.LocalDateTime;
import java.util.List;

import com.grandson.apigrandson.models.Comentario;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.ParceiroRepository;

import lombok.Getter;

@Getter
public class ParceiroAtualizaForm {

	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private String endereco;
	private Long cep;
	private String complemento;
	private int numero;
	private LocalDateTime dataInicio;
	
	
	public Parceiro atualizar(Long id, ParceiroRepository parceiroRepository) {
		Parceiro parceiro = parceiroRepository.getOne(id);
		parceiro.setNome(nome);
		parceiro.setTelefone(telefone);
		return parceiro;
	}

}
