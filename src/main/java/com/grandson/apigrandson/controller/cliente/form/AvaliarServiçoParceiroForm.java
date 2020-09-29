package com.grandson.apigrandson.controller.cliente.form;

import java.text.DecimalFormat;

import com.grandson.apigrandson.models.Comentario;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.ComentarioRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;
import com.grandson.apigrandson.repository.ServicoRepository;

import lombok.Data;

@Data
public class AvaliarServiçoParceiroForm {

	private int notaParceiro;
	private String comentario;

	public void avaliar(Servico servico, ParceiroRepository parceiroRepository, 
			ServicoRepository servicoRepository, ComentarioRepository comentarioRepository) {
		DecimalFormat f = new DecimalFormat("#.##");
		servico.setAvaliacaoParceiro(this.notaParceiro);
		
		Parceiro parceiro = parceiroRepository.getOne(servico.getParceiro().getId());
		double media = servicoRepository.getMediaAvaliacaoParceiro(parceiro);
		parceiro.setNota(f.format(media));
		
		if(!comentario.equals("") || comentario.length() > 0) {
			Comentario comentario = new Comentario();
			comentario.setCliente(servico.getCliente());
			comentario.setParceiro(parceiro);
			comentario.setComentarioParceiro(this.comentario);
			comentarioRepository.save(comentario);
		}
		
		servico.setStatus(StatusServico.AVALIADO);
	}
	
	
}
