package com.grandson.apigrandson.controller.cliente.form;

import java.time.LocalDate;

import com.grandson.apigrandson.models.CartaoDeCredito;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.repository.CartaoDeCreditoRepository;

import lombok.Data;

@Data
public class ClienteCartaoAtualizacaoForm {

	private String nomeCartao;
	private String numeroCartao;
	private Long cvv;
	private LocalDate dataValidade;
	
	
	public CartaoDeCredito atualiza(Cliente cliente, CartaoDeCreditoRepository cartaoDeCreditoRepository) {
		Long idCartao = cliente.getCartao().getId();
		CartaoDeCredito cartao = cartaoDeCreditoRepository.getOne(idCartao);
		cartao.setNomeDoCartao(nomeCartao);
		cartao.setNumeroDoCartao(numeroCartao);
		cartao.setCodigoDeSeguranca(cvv);
		cartao.setDataDeVencimento(dataValidade);
		return cartao;
	}
}
