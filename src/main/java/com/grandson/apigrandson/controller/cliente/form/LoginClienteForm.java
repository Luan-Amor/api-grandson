package com.grandson.apigrandson.controller.cliente.form;

import lombok.Data;

@Data
public class LoginClienteForm {

	private String email;
	private String senha;
	
	public LoginClienteForm() {}

	public LoginClienteForm(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	
}
