package com.grandson.apigrandson.controller.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grandson.apigrandson.config.security.TokenService;
import com.grandson.apigrandson.controller.cliente.dto.DetalharParceiroDto;
import com.grandson.apigrandson.controller.cliente.dto.DetalheCartaoDeCreditoDto;
import com.grandson.apigrandson.controller.cliente.dto.PerfilClienteDto;
import com.grandson.apigrandson.controller.cliente.form.ClienteAtualizacaoForm;
import com.grandson.apigrandson.controller.cliente.form.ClienteCartaoAtualizacaoForm;
import com.grandson.apigrandson.controller.cliente.form.ClienteForm;
import com.grandson.apigrandson.controller.cliente.form.EsqueciASenhaForm;
import com.grandson.apigrandson.controller.comum.dto.MensagensDto;
import com.grandson.apigrandson.controller.comum.form.AtualizarSenhaForm;
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
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/home")
	public List<ListaParceiroDto> listarParceiros(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			List<Parceiro> parceiros = parceiroRepository.findAll();
			return ListaParceiroDto.converte(parceiros);			
		}
		return new ArrayList<ListaParceiroDto>();
	}
	
	@GetMapping("/perfil/parceiro/{id}")
	public ResponseEntity<DetalharParceiroDto> detalhar(HttpServletRequest request, @PathVariable Long id){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Parceiro> parceiro = parceiroRepository.findById(id);
			if(parceiro.isPresent()) {
				return ResponseEntity.ok(new DetalharParceiroDto(parceiro.get()));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/perfil")
	public ResponseEntity<PerfilClienteDto> detalharCliente(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Optional<Cliente> cliente = clienteRespository.findById(id);
			if(cliente.isPresent()) {
				return ResponseEntity.ok(new PerfilClienteDto(cliente.get()));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/perfil/carteira")
	public ResponseEntity<DetalheCartaoDeCreditoDto> detalharCartaoDeCredito(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		
		if(tokenService.isTokenValido(token)) {			
			Long id = tokenService.getIdUsuario(token);
			
			Optional<Cliente> cliente = clienteRespository.findById(id);
			if(cliente.isPresent()) {
				Optional<CartaoDeCredito> cartao = cartaoDeCreditoRepository.findById(cliente.get().getCartao().getId());
				if(cartao.isPresent()) {
					return ResponseEntity.ok(new DetalheCartaoDeCreditoDto(cartao.get()));
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastrar")
	@Transactional
	public ResponseEntity<MensagensDto> cadastrar(@RequestBody @Valid ClienteForm form) {
		
		Cliente cliente = form.converter();
		if(clienteRespository.findByEmail(form.getEmail()).isPresent())
			return ResponseEntity.badRequest().body(new MensagensDto("O email informado já foi cadastrado."));
		
		if(clienteRespository.findByCpf(form.getCpf()).isPresent())
			return ResponseEntity.badRequest().body(new MensagensDto("O CPF informado já foi cadastrado."));
		
		try {			
			cartaoDeCreditoRepository.save(cliente.getCartao());
			enderecoRepository.save(cliente.getEndereco());
			Cliente client = clienteRespository.save(cliente);
			return ResponseEntity.ok(new MensagensDto("Cliente cadastrado com sucesso!", client.getId()) );
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<PerfilClienteDto> alterar(HttpServletRequest request, @RequestBody ClienteAtualizacaoForm form) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Optional<Cliente> optional = clienteRespository.findById(id);
			if(optional.isPresent()) {
				Cliente cliente = form.atualiza(id, clienteRespository);
				return ResponseEntity.ok(new PerfilClienteDto(cliente));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/carteira")
	@Transactional
	public ResponseEntity<?> alterarCartao(HttpServletRequest request, 
									@RequestBody ClienteCartaoAtualizacaoForm form) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Optional<Cliente> optional = clienteRespository.findById(id);
			if(optional.isPresent()) {
				form.atualiza(optional.get(), cartaoDeCreditoRepository);
				return ResponseEntity.ok(new MensagensDto("Cartão alterado com sucesso."));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("alterar/senha")
	@Transactional
	public ResponseEntity<MensagensDto> alterarSenha(HttpServletRequest request, @RequestBody AtualizarSenhaForm form){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Optional<Cliente> optional = clienteRespository.findById(id);
			if(optional.isPresent()) {
				String atualizarSenhaCliente = form.atualizarSenhaCliente(optional.get());
				return ResponseEntity.ok(new MensagensDto(atualizarSenhaCliente));
			}
		}
		
		return ResponseEntity.badRequest().build();
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


