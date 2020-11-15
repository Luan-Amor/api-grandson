package com.grandson.apigrandson.controller.cliente.form;

import lombok.Getter;

@Getter
public class MotivoCancelamentoForm {

	private String motivo;
	
	public MotivoCancelamentoForm(String motivo){
		this.motivo = motivo;
	}
}
