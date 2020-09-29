package com.grandson.apigrandson.controller.parceiro.form;

import java.text.DecimalFormat;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Comentario;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ComentarioRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;
import com.grandson.apigrandson.repository.ServicoRepository;

import lombok.Getter;

@Getter
public class AvaliarClienteForm {

	private int notaCliente;
	private String comentario;
	
	public void avaliar(Servico servico, ParceiroRepository parceiroRepository, ClienteRepository clienteRepository,
			ServicoRepository servicoRepository, ComentarioRepository comentarioRepository) {
		
		DecimalFormat f = new DecimalFormat("#.##");
		servico.setAvaliacaoCliente(notaCliente);
		
		Cliente cliente = clienteRepository.getOne(servico.getCliente().getId());
		double media = servicoRepository.getMediaAvaliacaoCliente(cliente);
		cliente.setNota(f.format(media));
		
		Parceiro parceiro = parceiroRepository.getOne(servico.getParceiro().getId());
		parceiro.setQuantidadeServicos(parceiro.getQuantidadeServicos() + 1);
		parceiro.setSaldo(parceiro.getSaldo() + (servico.getQuantidadeDeHoras() * 12.50));
		
		if(!comentario.equals("") || comentario.length() > 0) {
			Comentario comentario = new Comentario();
			comentario.setCliente(cliente);
			comentario.setParceiro(parceiro);
			comentario.setComentarioParceiro(this.comentario);
			comentarioRepository.save(comentario);
		}
		
		servico.setStatus(StatusServico.FINALIZADO);
	}
}