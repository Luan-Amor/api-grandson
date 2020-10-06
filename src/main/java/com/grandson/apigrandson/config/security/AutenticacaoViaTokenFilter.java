package com.grandson.apigrandson.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.grandson.apigrandson.models.Administrador;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.AdministradorRepository;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private ParceiroRepository parceiroRepository;
	private ClienteRepository clienteRepository;
	private AdministradorRepository admRepository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, ParceiroRepository parceiroRepository,
			ClienteRepository clienteRepository, AdministradorRepository admRepository) {
		this.tokenService = tokenService;
		this.parceiroRepository = parceiroRepository;
		this.clienteRepository = clienteRepository;
		this.admRepository = admRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isTokenValido(token);
		if(valido) {
			autenticarUsuario(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarUsuario(String token) {
		Long id = tokenService.getIdUsuario(token);
		
		Optional<Parceiro> parceiro = parceiroRepository.findById(id);
		if(parceiro.isPresent()) {			
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(parceiro.get(), null, null); 
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(cliente.get(), null, null); 
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		Optional<Administrador> adm = admRepository.findById(id);
		if(adm.isPresent()) {
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(adm.get(), null, adm.get().getPerfis()); 
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
