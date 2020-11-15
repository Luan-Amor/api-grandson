package com.grandson.apigrandson.controller.parceiro.form;

import com.grandson.apigrandson.models.Parceiro;

import lombok.Getter;

@Getter
public class ParceiroAtualizaForm {

	private String nome;
	private String telefone;
	
	private String endereco;
	private Long cep;
	private String complemento;
	private int numero;
	private String cidade;
	private String estado;
	
	
	public Parceiro atualizar(Parceiro parceiro) {
		
		if(nome != null && !nome.isEmpty())
			parceiro.setNome(nome);
		if(telefone != null && !telefone.isEmpty())
			parceiro.setTelefone(telefone);
		
		if(cep != null && cep != 0)
			parceiro.getEndereco().setCep(cep);
		if(endereco != null && !endereco.isEmpty())
			parceiro.getEndereco().setEndereco(endereco);
		if(numero != 0)
			parceiro.getEndereco().setNumero(numero);
		if(complemento != null && !complemento.isEmpty())
			parceiro.getEndereco().setComplemento(complemento);
		
		parceiro.getEndereco().setEstado(estado);
		parceiro.getEndereco().setCidade(cidade);
		
		return parceiro;
	}

}
