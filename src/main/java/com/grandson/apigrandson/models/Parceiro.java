package com.grandson.apigrandson.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
public class Parceiro implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
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
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	private List<Perfil> perfis = new ArrayList<>();
	
	@OneToMany(mappedBy="parceiro")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@OneToOne
	private Endereco endereco;
	
	@OneToOne
	private ContaCorrente conta = null;
	
	@OneToMany(mappedBy="parceiro")
	private List<Servico> servicos = new ArrayList<Servico>();
	
	@OneToOne
	private Foto foto;
	
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.getSenha();
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
