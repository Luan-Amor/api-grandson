package com.grandson.apigrandson.controller.comum;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grandson.apigrandson.config.security.TokenService;
import com.grandson.apigrandson.controller.comum.dto.TokenDto;
import com.grandson.apigrandson.controller.comum.form.LoginForm;

@RestController
@RequestMapping("api/")
public class AutencicacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/auth/parceiro")
	public ResponseEntity<TokenDto> autenticarParceiro(@RequestBody @Valid LoginForm form){
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarTokenParceiro(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (org.springframework.security.core.AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/auth/cliente")
	public ResponseEntity<TokenDto> autenticarCliente(@RequestBody @Valid LoginForm form){
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarTokenCliente(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (org.springframework.security.core.AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/auth/administrador")
	public ResponseEntity<TokenDto> autenticarAdm(@RequestBody @Valid LoginForm form){
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarTokenAdm(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (org.springframework.security.core.AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
