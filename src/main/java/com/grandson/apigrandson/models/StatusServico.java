package com.grandson.apigrandson.models;

public enum StatusServico {

	CONCLUIDO, 
	FINALIZADO,
	AVALIADO,
	ACEITO,
	PENDENTE,
	CANCELADO;
	
	public boolean isAtivo() {
		if(this != StatusServico.CONCLUIDO || this != StatusServico.CANCELADO
				|| this != StatusServico.FINALIZADO || this != StatusServico.AVALIADO)
			return true;
		return false;
	}
}
