package com.grandson.apigrandson.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
public class Cliente implements UserDetails, Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String cpf;
	@Column(unique = true)
	private String telefone;
	private LocalDateTime dataInicio = LocalDateTime.now();
	private String senha;
	private String nota = "3";
	private Long quantidadeServicos = 0l;
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	private List<Perfil> perfis = new ArrayList<>();
	
	@OneToMany(mappedBy="cliente")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	@OneToOne
	private CartaoDeCredito cartao;
	
	@OneToOne
	private Endereco endereco;
	
	@OneToOne
	private Foto foto;
	
	@OneToMany(mappedBy="cliente")
	private List<Servico> servicos = new ArrayList<Servico>();
	
	public Cliente() {}
	
	public Cliente(String nome, String email, String cpf, String senha,String telefone, Endereco endereco, CartaoDeCredito cartao) {
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.senha = senha;
		this.endereco = endereco;
		this.cartao = cartao;
		this.telefone = telefone;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.senha;
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