package com.grandson.apigrandson.controller.cliente.form;

import java.time.LocalDateTime;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

import lombok.Getter;

@Getter
public class FormNovoServico {

	private LocalDateTime horario;
	private double quantidadeDeHoras;
	private double valor;
	private Long idCliente;
	private Long idParceiro;
	
	public static Servico converte(FormNovoServico form, ParceiroRepository parceiroRepository,
			ClienteRepository clienteRepository) {
		Servico servico = new Servico(form);
		
		try {			
			Parceiro parceiro = parceiroRepository.getOne(form.idParceiro);
			Cliente cliente = clienteRepository.getOne(form.idCliente);
			servico.setCliente(cliente);
			servico.setParceiro(parceiro);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return servico;
	}
	
	

	
	
}
