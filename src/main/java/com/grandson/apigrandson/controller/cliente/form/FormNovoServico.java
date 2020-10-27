package com.grandson.apigrandson.controller.cliente.form;

import java.time.LocalDateTime;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Endereco;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.EnderecoRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

import lombok.Getter;

@Getter
public class FormNovoServico {

	private LocalDateTime horario;
	private double quantidadeDeHoras;
	private double valor;
	private Long idParceiro;
	
	private Long cep;
	private String endereco;
	private int numero;
	private String complemento;  
	private String cidade;
	private String estado;
	private String descricao;
	
	public static Servico converte(FormNovoServico form, ParceiroRepository parceiroRepository,
			Cliente cliente, EnderecoRepository enderecoRepositoy) {

		try {			
			Parceiro parceiro = parceiroRepository.getOne(form.idParceiro);
			Endereco end = new Endereco(form.cep, form.endereco, form.numero, form.complemento,
			form.cidade, form.estado);
			enderecoRepositoy.save(end);
			return new Servico(form, cliente, parceiro, end);
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return null;
	}
	
	

	
	
}
