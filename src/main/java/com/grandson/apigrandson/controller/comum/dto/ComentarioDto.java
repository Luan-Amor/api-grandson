package com.grandson.apigrandson.controller.comum.dto;

import com.grandson.apigrandson.models.Comentario;

import lombok.Getter;

@Getter
public class ComentarioDto {

	private byte[] foto;
	private String nome;
	private String texto;
	
	public ComentarioDto(Comentario comentario){
		nome = comentario.getCliente().getNome();
		texto = comentario.getComentarioParceiro();
		
		if(comentario.getCliente().getFoto() != null) {
			this.foto = comentario.getCliente().getFoto().getData();
		}
	}
	
}
