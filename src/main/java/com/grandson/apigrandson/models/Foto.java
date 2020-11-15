package com.grandson.apigrandson.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String type;
	
	private byte[] data;
	
	public Foto() {}

	public Foto(String nome, String type, byte[] data) {
		this.nome = nome;
		this.type = type;
		this.data = data;
	}
	
}
