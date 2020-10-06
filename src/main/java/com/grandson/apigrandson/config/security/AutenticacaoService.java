package com.grandson.apigrandson.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grandson.apigrandson.models.Administrador;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.AdministradorRepository;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private AdministradorRepository admRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Parceiro> parceiro = parceiroRepository.findByEmail(email);
		if(parceiro.isPresent())
			return parceiro.get();
	
		Optional<Cliente> cliente = clienteRepository.findByEmail(email);
		if(cliente.isPresent())
			return cliente.get();
		
		Optional<Administrador> administrador = admRepository.findByEmail(email);
		if(administrador.isPresent())
			return administrador.get();
		
		
		throw new UsernameNotFoundException("Dados Inválidos");
	}

}
