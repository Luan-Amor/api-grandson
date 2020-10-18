package com.grandson.apigrandson.controller.parceiro.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.grandson.apigrandson.models.ContaCorrente;
import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.util.GerarHashSenhaUtil;

import lombok.Getter;

@Getter
public class ParceiroForm {

	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @NotEmpty
	@Email
	private String email;
	
	@NotNull @NotEmpty
	@Digits(fraction = 0, integer = 11)
	private String cpf;
	
	@NotNull @NotEmpty
	@Size(min = 9, max = 13)
	@Digits(fraction = 0, integer = 13)
	private String telefone;
	
	@NotNull @NotEmpty
	private String senha;
	
	private String endereco;
	private String complemento;
	private int numero;
	private Long cep;
	private String cidade;
	private String estado;
	
	private int agencia;
	private int conta;
	private String banco;
	private String tipo;
	
	public Parceiro converter() {
		
		String hash = GerarHashSenhaUtil.gerarHash(senha);
		
		Endereco endereco = new Endereco(cep, this.endereco, numero, complemento, cidade, estado);
		ContaCorrente cc = new ContaCorrente(agencia, conta, banco, tipo);
		return new Parceiro(nome, email, cpf, telefone, hash, endereco, cc);
	}

}
