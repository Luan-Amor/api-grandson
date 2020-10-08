package com.grandson.apigrandson.controller.comum;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.grandson.apigrandson.repository.ServicoRepository;

@Service
public class ScheduleTask {

	@Autowired
	private ServicoRepository servicos;
	
	@Scheduled(fixedRate = 5000)
	public void atualizaServicoVencidos() {
		try {
			
		} catch (Exception e) {
		}
	}
}
