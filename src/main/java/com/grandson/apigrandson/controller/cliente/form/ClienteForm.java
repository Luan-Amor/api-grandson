package com.grandson.apigrandson.controller.cliente.form;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.grandson.apigrandson.models.CartaoDeCredito;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.repository.FotoRepository;

import lombok.Data;

@Data
public class ClienteForm {

	@NotNull @NotEmpty
	private String nome;
	@NotNull @NotEmpty
	private String email;
	@NotNull @NotEmpty
	private String cpf;
	@NotNull @NotEmpty
	private String senha;
	@NotNull @NotEmpty
	private String telefone;
	
	private String endereco;
	private Long cep;
	private int numero;
	private String complemento;
	
	private String nomeCartao;
	private String numeroCartao;
	private long cvv;
	private LocalDate dataValidade;
	
	public Cliente converter() {
		Date data = Date.from(dataValidade.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		Endereco endereco = new Endereco(cep, this.endereco, numero, complemento);
		CartaoDeCredito cartao = new CartaoDeCredito(nomeCartao, numeroCartao, cvv, data);
		return new Cliente(this.nome, this.email, this.cpf, this.senha, telefone, endereco, cartao);
	}

}
