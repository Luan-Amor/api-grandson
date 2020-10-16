package com.grandson.apigrandson.controller.comum.form;

import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.util.GerarHashSenhaUtil;

import lombok.Getter;

@Getter
public class AtualizarSenhaForm {

	private String senha;
	private String novaSenha;
	
	
	public String atualizarSenhaCliente(Cliente cliente) {
		
		if(GerarHashSenhaUtil.isPasswordMatch(senha, cliente.getSenha())) {
			String newPass = GerarHashSenhaUtil.gerarHash(novaSenha);
			cliente.setSenha(newPass);
			return "Senha alterada com sucesso.";
		}
		
		return "Senha anterior inválida";
	}
	
	public String atualizarSenhaParceiro(Parceiro parceiro) {
		
		if(GerarHashSenhaUtil.isPasswordMatch(senha, parceiro.getSenha())) {
			String newPass = GerarHashSenhaUtil.gerarHash(novaSenha);
			
			parceiro.setSenha(newPass);
			
			return "Senha alterada com sucesso.";
		}
		
		return "Senha anterior inválida";
	}
	
	
}
