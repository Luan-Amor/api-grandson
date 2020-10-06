package com.grandson.apigrandson.controller.parceiro.form;

import com.grandson.apigrandson.models.ContaCorrente;
import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.models.Parceiro;

import lombok.Getter;

@Getter
public class ParceiroForm {

	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private String senha;
	
	private String endereco;
	private String complemento;
	private int numero;
	private Long cep;
	
	private String foto;
	private String type;
	private String nomeFoto;
	
	private int agencia;
	private int conta;
	private String banco;
	private String tipo;
	
	public Parceiro converter() {
		
		Foto foto = new Foto(nomeFoto, type, this.foto.getBytes());
		Endereco endereco = new Endereco(cep, this.endereco, numero, complemento);
		ContaCorrente cc = new ContaCorrente(agencia, conta, banco, tipo);
		return new Parceiro(nome, email, cpf, telefone, senha, endereco, cc, foto);
	}

}
