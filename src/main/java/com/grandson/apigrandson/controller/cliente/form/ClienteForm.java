package com.grandson.apigrandson.controller.cliente.form;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.grandson.apigrandson.models.CartaoDeCredito;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.repository.FotoRepository;
import com.grandson.apigrandson.util.GerarHashSenhaUtil;

import lombok.Data;

@Data
public class ClienteForm {

	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @NotEmpty
	@Email
	private String email;
	
	@NotNull @NotEmpty
	@Digits(fraction = 0, integer = 11)
	private String cpf;
	
	@NotNull @NotEmpty
	private String senha;
	
	@NotNull @NotEmpty
	@Size(min = 9, max = 13)
	@Digits(fraction = 0, integer = 13)
	private String telefone;
	
	private String endereco;
	private Long cep;
	private int numero;
	private String complemento;
	private String cidade;
	private String estado;
	
	private String nomeCartao;
	
	@Size(min = 15, max = 16, message = "O cartão deve possuir 15 ou 16 dígitos.")
	@Digits(fraction = 0, integer = 16)
	private String numeroCartao;
	private long cvv;
	private LocalDate dataValidade;
	
	public Cliente converter() {
		Date data = Date.from(dataValidade.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		String hash = GerarHashSenhaUtil.gerarHash(senha);
		
		Endereco endereco = new Endereco(cep, this.endereco, numero, complemento, cidade, estado);
		CartaoDeCredito cartao = new CartaoDeCredito(nomeCartao, numeroCartao, cvv, data);
		return new Cliente(this.nome, this.email, this.cpf, hash, telefone, endereco, cartao);
	}

}
