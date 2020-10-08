package com.grandson.apigrandson.controller.parceiro.form;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.grandson.apigrandson.models.Comentario;
import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.EnderecoRepository;
import com.grandson.apigrandson.repository.FotoRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

import lombok.Getter;

@Getter
public class ParceiroAtualizaForm {

	@NotNull @NotEmpty
	private String nome;
	@NotNull @NotEmpty
	private String cpf;
	@NotNull @NotEmpty
	private String email;
	@NotNull @NotEmpty
	private String telefone;
	
	private String endereco;
	private Long cep;
	private String complemento;
	private int numero;
	
	
	public Parceiro atualizar(Long id, ParceiroRepository parceiroRepository, 
			EnderecoRepository enderecoRepository, FotoRepository fotoRepository) {
		
		Parceiro parceiro = parceiroRepository.getOne(id);
		parceiro.setNome(nome);
		parceiro.setTelefone(telefone);
		parceiro.setCpf(cpf);
		parceiro.setEmail(email);
		
		Endereco endereco2 = enderecoRepository.getOne(parceiro.getEndereco().getId());
		endereco2.setCep(cep);
		endereco2.setEndereco(endereco);
		endereco2.setNumero(numero);
		endereco2.setComplemento(complemento);
		
		return parceiro;
	}

}
