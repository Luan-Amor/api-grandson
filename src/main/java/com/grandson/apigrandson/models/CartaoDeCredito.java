package com.grandson.apigrandson.models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CartaoDeCredito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomeDoCartao;
	private String numeroDoCartao;
	private long codigoDeSeguranca;
	private LocalDate dataDeVencimento;
	
	public CartaoDeCredito() {}
	
	public CartaoDeCredito(String nomeCartao, String numeroCartao, long cvv, Date dataValidade) {
		this.nomeDoCartao = nomeCartao;
		this.numeroDoCartao = numeroCartao;
		this.codigoDeSeguranca = cvv;
		this.dataDeVencimento = dataValidade.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
