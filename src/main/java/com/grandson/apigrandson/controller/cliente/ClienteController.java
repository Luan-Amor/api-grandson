package com.grandson.apigrandson.controller.cliente;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.grandson.apigrandson.config.security.TokenService;
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
import com.grandson.apigrandson.repository.FotoRepository;
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
	
	@Autowired
	private FotoRepository fotoRepository;
	
	@Autowired
	private TokenService tokenService;
	
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
	
	@GetMapping("/perfil")
	public ResponseEntity<PerfilClienteDto> detalharCliente(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		Long id = tokenService.getIdUsuario(token);
		
		Optional<Cliente> cliente = clienteRespository.findById(id);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(new PerfilClienteDto(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/perfil/carteira")
	public ResponseEntity<DetalheCartaoDeCreditoDto> detalharCartaoDeCredito(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		Long id = tokenService.getIdUsuario(token);
		
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
	public ResponseEntity<PerfilClienteDto> cadastrar(@RequestBody @Valid ClienteForm form, UriComponentsBuilder uriBuilder) {
		
		Cliente cliente = form.converter();
		
		fotoRepository.save(cliente.getFoto());
		cartaoDeCreditoRepository.save(cliente.getCartao());
		enderecoRepository.save(cliente.getEndereco());
		clienteRespository.save(cliente);
		
		URI uri = uriBuilder.path("/perfil/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(new PerfilClienteDto(cliente));
	}
	
//	@DeleteMapping("/deletar")
//	@Transactional
//	public ResponseEntity<?> deletar(HttpServletRequest request) {
//		String token = tokenService.recuperarToken(request);
//		Long id = tokenService.getIdUsuario(token);
//		
//		Optional<Cliente> cliente = clienteRespository.findById(id);
//		
//		if(cliente.isPresent()) {
//			clienteRespository.deleteById(id);
//			return ResponseEntity.ok().build();
//		}
//		return ResponseEntity.notFound().build();
//	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<PerfilClienteDto> alterar(HttpServletRequest request, @RequestBody ClienteAtualizacaoForm form) {
		String token = tokenService.recuperarToken(request);
		Long id = tokenService.getIdUsuario(token);
		
		Optional<Cliente> optional = clienteRespository.findById(id);
		if(optional.isPresent()) {
			Cliente cliente = form.atualiza(id, clienteRespository);
			return ResponseEntity.ok(new PerfilClienteDto(cliente));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/carteira")
	@Transactional
	public ResponseEntity<DetalheCartaoDeCreditoDto> alterarCartao(HttpServletRequest request, 
									@RequestBody ClienteCartaoAtualizacaoForm form) {
		String token = tokenService.recuperarToken(request);
		Long id = tokenService.getIdUsuario(token);
		
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


