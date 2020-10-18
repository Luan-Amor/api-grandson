package com.grandson.apigrandson.controller.comum;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.ServicoRepository;

@Service
public class ScheduleTask {

	@Autowired
	private ServicoRepository servicos;
	
	LocalDateTime hoje;
	
	@Scheduled(fixedRate = 100000)
	@Transactional
	public void atualizaServicoVencidos() {
		
		try {
			hoje = LocalDateTime.now();
			List<Servico> servicosVencidos = servicos.findAllServicosVencidos(hoje, StatusServico.CONCLUIDO);
			if(servicosVencidos.isEmpty()) {
				System.out.println("Lista Vazia: " + hoje);
			}else {
				System.out.println("Lista preenchida " + hoje);
				servicosVencidos.stream().forEach(servico -> {
					servico.setStatus(StatusServico.CONCLUIDO);
				});
			}
		} catch (Exception e) {
		}
	}
	
}
