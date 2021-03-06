package com.grandson.apigrandson.controller.parceiro.dto;

import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.models.Comentario;

import lombok.Data;

@Data
public class ComentariosParceiroDto {

	private FotoDto foto;
	private String nome;
	private String texto;
	
	public ComentariosParceiroDto(Comentario comentario){
			this.nome = comentario.getCliente().getNome();
			this.texto = comentario.getComentarioCliente();
			this.foto = new FotoDto(comentario.getCliente().getFoto());
	}
	
}
