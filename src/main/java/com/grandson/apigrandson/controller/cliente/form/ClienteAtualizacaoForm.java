package com.grandson.apigrandson.controller.cliente.form;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.repository.ClienteRepository;

import lombok.Data;

@Data
public class ClienteAtualizacaoForm {

	private String nome;
	private String telefone;
	private String endereco;
	private Long cep;
	private String complemento;
	private int numero;
	private String senha;
	
	
	public Cliente atualiza(Long id, ClienteRepository clienteRespository) {
		Cliente cliente = clienteRespository.getOne(id);
		cliente.setNome(nome);
		cliente.setTelefone(telefone);
		cliente.getEndereco().setEndereco(endereco);
		cliente.getEndereco().setCep(cep);
		cliente.getEndereco().setComplemento(complemento);
		cliente.getEndereco().setNumero(numero);
		cliente.setSenha(senha);
		return cliente;
	}
	
}
