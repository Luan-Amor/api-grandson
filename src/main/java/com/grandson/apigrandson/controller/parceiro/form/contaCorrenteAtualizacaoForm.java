package com.grandson.apigrandson.controller.parceiro.form;

import com.grandson.apigrandson.models.ContaCorrente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.ContaCorrenteRepository;

public class contaCorrenteAtualizacaoForm {

	private int agencia;
	private int conta;
	private String banco;
	private String tipo;
	
	public ContaCorrente atualiza(Parceiro parceiro, ContaCorrenteRepository contaCorrenteRepository) {
		try {
			ContaCorrente contaCorrente = contaCorrenteRepository.getOne(parceiro.getId());
			contaCorrente.setAgencia(agencia);
			contaCorrente.setConta(conta);
			contaCorrente.setBanco(banco);
			contaCorrente.setTipo(tipo);
			
			return contaCorrente;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}

}
