package com.grandson.apigrandson.controller.cliente;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.grandson.apigrandson.controller.cliente.dto.ClienteListaDto;
import com.grandson.apigrandson.controller.cliente.dto.DetalheCartaoDeCreditoDto;
import com.grandson.apigrandson.controller.cliente.dto.DetalheClienteDto;
import com.grandson.apigrandson.controller.cliente.dto.PerfilClienteDto;
import com.grandson.apigrandson.controller.cliente.form.ClienteAtualizacaoForm;
import com.grandson.apigrandson.controller.cliente.form.ClienteCartaoAtualizacaoForm;
import com.grandson.apigrandson.controller.cliente.form.ClienteForm;
import com.grandson.apigrandson.controller.cliente.form.EsqueciASenhaForm;
import com.grandson.apigrandson.controller.cliente.form.LoginClienteForm;
import com.grandson.apigrandson.controller.parceiro.dto.PerfilParceiroDto;
import com.grandson.apigrandson.controller.parceiro.dto.ListaParceiroDto;
import com.grandson.apigrandson.models.CartaoDeCredito;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.CartaoDeCreditoRepository;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.EnderecoRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRespository;
	
	@Autowired
	private ParceiroRepository parceiroRepository; 
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CartaoDeCreditoRepository cartaoDeCreditoRepository;
	
//	@GetMapping
//	public List<ClienteListaDto> lista(){
//		List<Cliente> clientes = clienteRespository.findAll();
//		return ClienteListaDto.converte(clientes);
//	}
	
	/*
	 * |URL's Cliente
	 * |Home 					| Lista de parceiros 		|OK
	 * |Agendamento 			| Lista Serviços Pendentes	|OK
	 * |Perfil					| Detalhe Cliente			|OK
	 * |Cadastrar Cliente		| HTTP 200					|Ok
	 * |Detalhar Parceiro		| detalhe parceiro			|OK
	 * |Cartao de Credito		| Detalhe cartao de credito	|OK
	 * |Alterar dados Cliente	| Detalhe cliente			|Ok
	 * |Alterar dados Cartão	| Detalhe Cartão			|OK
	 * |Deletar dados Cartão	| Detalhe Cartão			|OK
	 * |#######################################################
	 * |Deletar Cliente 		| Http 200/404				|
	 * |Esqueci a senha			| SMS						|
	 * 
	 * */
	
	
	@GetMapping("/home")
	public List<ListaParceiroDto> listarParceiros(){
		List<Parceiro> parceiros = parceiroRepository.findAll();
		return ListaParceiroDto.converte(parceiros);
	}
	
	@GetMapping("/perfil/parceiro/{id}")
	public ResponseEntity<PerfilParceiroDto> detalhar(@PathVariable Long id){
		Optional<Parceiro> parceiro = parceiroRepository.findById(id);
		if(parceiro.isPresent()) {
			return ResponseEntity.ok(new PerfilParceiroDto(parceiro.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<DetalheClienteDto> login(@RequestBody LoginClienteForm form){
		Optional<Cliente> cliente = clienteRespository.findByEmail(form.getEmail());
		if(cliente.isPresent()) {
			ResponseEntity.ok(new DetalheClienteDto(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/perfil/{id}")
	public ResponseEntity<PerfilClienteDto> detalharCliente(@PathVariable long id){
		Optional<Cliente> cliente = clienteRespository.findById(id);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(new PerfilClienteDto(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/perfil/carteira/{id}")
	public ResponseEntity<DetalheCartaoDeCreditoDto> detalharCartaoDeCredito(@PathVariable long id){
		Optional<Cliente> cliente = clienteRespository.findById(id);
		if(cliente.isPresent()) {
			Optional<CartaoDeCredito> cartao = cartaoDeCreditoRepository.findById(cliente.get().getCartao().getId());
			if(cartao.isPresent()) {
				return ResponseEntity.ok(new DetalheCartaoDeCreditoDto(cartao.get()));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastrar")
	@Transactional
	public ResponseEntity<PerfilClienteDto> cadastrar(@RequestBody ClienteForm form, UriComponentsBuilder uriBuilder) {
		Cliente cliente = form.converter();
		
		cartaoDeCreditoRepository.save(cliente.getCartao());
		enderecoRepository.save(cliente.getEndereco());
		clienteRespository.save(cliente);
		
		URI uri = uriBuilder.path("/perfil/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(new PerfilClienteDto(cliente));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRespository.findById(id);
		
		if(cliente.isPresent()) {
			clienteRespository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PerfilClienteDto> alterar(@PathVariable Long id, @RequestBody ClienteAtualizacaoForm form) {
		Optional<Cliente> optional = clienteRespository.findById(id);
		if(optional.isPresent()) {
			Cliente cliente = form.atualiza(id, clienteRespository);
			return ResponseEntity.ok(new PerfilClienteDto(cliente));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/carteira/{id}")
	@Transactional
	public ResponseEntity<DetalheCartaoDeCreditoDto> alterarCartao(@PathVariable Long id, 
									@RequestBody ClienteCartaoAtualizacaoForm form) {
		Optional<Cliente> optional = clienteRespository.findById(id);
		if(optional.isPresent()) {
			CartaoDeCredito cartao = form.atualiza(optional.get(), cartaoDeCreditoRepository);
			return ResponseEntity.ok(new DetalheCartaoDeCreditoDto(cartao));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/esquecisenha")
	public ResponseEntity<?> esqueciASenha(@RequestBody EsqueciASenhaForm email){
		Optional<Cliente> cliente = clienteRespository.findByEmail(email.getEmail());
		if(cliente.isPresent()) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}


