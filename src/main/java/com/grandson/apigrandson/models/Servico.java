package com.grandson.apigrandson.models;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.grandson.apigrandson.controller.cliente.form.FormNovoServico;

import lombok.Data;

@Data
@Entity
public class Servico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double valor;
	private int quantidadeDeHoras;
	private LocalDateTime horario;
	private int avaliacaoCliente = 3;
	private int avaliacaoParceiro = 3;
	private LocalDateTime horarioDoPedido = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private StatusServico status = StatusServico.PENDENTE;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Parceiro parceiro;

	public Servico() {}
	
	public Servico(FormNovoServico form) {
		this.valor = (form.getValor());
		this.quantidadeDeHoras = form.getQuantidadeDeHoras();
		this.horario = form.getHorario();
	}
	
}
