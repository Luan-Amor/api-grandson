package com.grandson.apigrandson.controller.cliente.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.repository.ClienteRepository;

import lombok.Data;

@Data
public class ClienteAtualizacaoForm {
	
	@NotNull @NotEmpty
	private String nome;
	@NotNull @NotEmpty
	private String telefone;
	@NotNull @NotEmpty
	private String endereco;
	@NotNull @NotEmpty
	private Long cep;
	@NotNull @NotEmpty
	private String complemento;
	@NotNull @NotEmpty
	private int numero;
	@NotNull @NotEmpty
	private String senha;
	
	private Foto foto;
	
	public Cliente atualiza(Long id, ClienteRepository clienteRespository) {
		Cliente cliente = clienteRespository.getOne(id);
		cliente.setNome(nome);
		cliente.setTelefone(telefone);
		cliente.getEndereco().setEndereco(endereco);
		cliente.getEndereco().setCep(cep);
		cliente.getEndereco().setComplemento(complemento);
		cliente.getEndereco().setNumero(numero);
		cliente.setSenha(senha);
		if(foto != null)
			cliente.setFoto(foto);
		
		return cliente;
	}
	
}
