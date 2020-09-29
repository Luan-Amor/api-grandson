package com.grandson.apigrandson.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Parceiro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private String senha;
	private double saldo = 0;
	private String nota = "3";
	private Long quantidadeServicos = 0l;
	private LocalDateTime dataInicio = LocalDateTime.now();
	
	@OneToMany(mappedBy="parceiro")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@OneToOne(fetch = FetchType.LAZY)
	private Endereco endereco;
	
	@OneToOne(fetch = FetchType.LAZY)
	private ContaCorrente conta = null;
	
	@OneToMany(mappedBy="parceiro")
	private List<Servico> servicos = new ArrayList<Servico>();
	
	
	public Parceiro() {}
	
	public Parceiro(String nome, String email, String cpf, String telefone, 
			String senha, Endereco endereco, ContaCorrente cc) {
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
		this.senha = senha;
		this.endereco = endereco;
		this.conta = cc;
	}
	
}
