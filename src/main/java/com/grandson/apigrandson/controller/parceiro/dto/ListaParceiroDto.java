package com.grandson.apigrandson.controller.parceiro.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.models.Parceiro;

import lombok.Getter;

@Getter
public class ListaParceiroDto {
	
	private Long id;
	private FotoDto foto;
	private String nome;
	private String nota;
	
	public ListaParceiroDto() {}

	public ListaParceiroDto(Parceiro parceiro) {
		this.id = parceiro.getId();
		this.nome = parceiro.getNome();
		this.nota = parceiro.getNota();
		this.foto = new FotoDto(parceiro.getFoto());
	}

	public static Page<ListaParceiroDto> converte(Page<Parceiro> parceiro) {
		return parceiro.map(ListaParceiroDto::new);
	}

}
