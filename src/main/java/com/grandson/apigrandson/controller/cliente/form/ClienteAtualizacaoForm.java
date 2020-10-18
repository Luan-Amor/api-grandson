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
	private String cidade;
	private String estado;
	
	public Cliente atualiza(Long id, ClienteRepository clienteRespository) {
		Cliente cliente = clienteRespository.getOne(id);
		
		if(nome != null && !nome.isEmpty())
			cliente.setNome(nome);
		if(telefone != null && !telefone.isEmpty())
			cliente.setTelefone(telefone);
		if(endereco != null && !endereco.isEmpty())
			cliente.getEndereco().setEndereco(endereco);
		if(cep != null)
			cliente.getEndereco().setCep(cep);
		if(complemento != null && !complemento.isEmpty())
			cliente.getEndereco().setComplemento(complemento);
		if(numero != 0)
			cliente.getEndereco().setNumero(numero);
		if(estado != null) 
			cliente.getEndereco().setEstado(estado);
		if(cidade != null)
			cliente.getEndereco().setCidade(cidade);
		
		return cliente;
	}
	
}
