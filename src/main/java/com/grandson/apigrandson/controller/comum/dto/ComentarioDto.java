package com.grandson.apigrandson.controller.comum.dto;

import com.grandson.apigrandson.models.Comentario;

import lombok.Getter;

@Getter
public class ComentarioDto {

	private FotoDto foto;
	private String nome;
	private String texto;
	
	public ComentarioDto(Comentario comentario){
			this.nome = comentario.getCliente().getNome();
			this.texto = comentario.getComentarioParceiro();
			this.foto = new FotoDto(comentario.getCliente().getFoto());
	}
	
}
