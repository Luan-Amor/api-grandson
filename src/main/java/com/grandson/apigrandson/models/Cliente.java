package com.grandson.apigrandson.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Cliente implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private LocalDateTime dataInicio = LocalDateTime.now();
	private String senha;
	private String nota = "3";
	
	@OneToMany(mappedBy="cliente")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@OneToOne
	private CartaoDeCredito cartao;
	
	@OneToOne
	private Endereco endereco;
	
	@OneToMany(mappedBy="cliente")
	private List<Servico> servicos = new ArrayList<Servico>();
	
	public Cliente() {}
	
	public Cliente(String nome, String email, String cpf, String senha, Endereco endereco, CartaoDeCredito cartao) {
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.senha = senha;
		this.endereco = endereco;
		this.cartao = cartao;
	}
}