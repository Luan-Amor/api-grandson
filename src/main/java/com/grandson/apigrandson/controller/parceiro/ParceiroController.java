package com.grandson.apigrandson.controller.parceiro;

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

import com.grandson.apigrandson.controller.cliente.dto.DetalheCartaoDeCreditoDto;
import com.grandson.apigrandson.controller.cliente.dto.DetalheClienteDto;
import com.grandson.apigrandson.controller.cliente.form.ClienteCartaoAtualizacaoForm;
import com.grandson.apigrandson.controller.cliente.form.EsqueciASenhaForm;
import com.grandson.apigrandson.controller.cliente.form.LoginClienteForm;
import com.grandson.apigrandson.controller.parceiro.dto.PerfilParceiroDto;
import com.grandson.apigrandson.controller.parceiro.dto.DetalheContaCorrenteDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicoDisponiveisDto;
import com.grandson.apigrandson.controller.parceiro.form.ParceiroAtualizaForm;
import com.grandson.apigrandson.controller.parceiro.form.ParceiroForm;
import com.grandson.apigrandson.controller.parceiro.form.contaCorrenteAtualizacaoForm;
import com.grandson.apigrandson.models.CartaoDeCredito;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.ContaCorrente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ContaCorrenteRepository;
import com.grandson.apigrandson.repository.EnderecoRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;
import com.grandson.apigrandson.repository.ServicoRepository;

@RestController
@RequestMapping("api/parceiro")
public class ParceiroController{

	/*
	 * | URL's Parceiro |
	 * | 
	 * | Login						| TokenDto					|Ok
	 * | Home						| lista servicos Pendentes	|OK
	 * | Perfil Cliente				| Detalhe Cliente			|Ok
	 * | Perfil						| Detalhe Parceiro			|OK
	 * | Cadastrar Parceiro			| Detalhe Parceiro			|Ok
	 * | Alterar parceiro			| Perfil Parceiro			|
	 * | Detalhar Cartão			| Detalhe Cartão			|Ok
	 * | Alterar Cartão				| Detalhe Cartão			|Ok
	 * | Esqueci a senha			| SMS /  EMAIL				|Ok
	 * */
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired EnderecoRepository enderecoRepository;
	
	@GetMapping("/home/{id}")
	public List<ServicoDisponiveisDto> servicosDisponiveis(@PathVariable Long id){
		Parceiro parceiro = parceiroRepository.getOne(id);
		List<Servico> servicos = servicoRepository.findServicosStatus(parceiro, StatusServico.PENDENTE);
		return ServicoDisponiveisDto.converte(servicos);
	}
	
	@GetMapping("/perfil/{id}")
	public ResponseEntity<PerfilParceiroDto> perfil(@PathVariable Long id){
		Optional<Parceiro> parceiro = parceiroRepository.findById(id);
		if(parceiro.isPresent()) {
			return ResponseEntity.ok(new PerfilParceiroDto(parceiro.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/perfil/cliente/{id}")
	public ResponseEntity<DetalheClienteDto> perfilCliente(@PathVariable Long id){
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(new DetalheClienteDto(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<PerfilParceiroDto> login(@RequestBody LoginClienteForm form){
		Optional<Parceiro> cliente = parceiroRepository.findByEmail(form.getEmail());
		if(cliente.isPresent()) {
			ResponseEntity.ok(new PerfilParceiroDto(cliente.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastrar")
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody ParceiroForm form){
		try {
			Parceiro parceiro = form.converter();
			
			enderecoRepository.save(parceiro.getEndereco());
			contaCorrenteRepository.save(parceiro.getConta());
			parceiroRepository.save(parceiro);
			
			return ResponseEntity.ok().build();			
		} catch (Exception e) {
			System.err.println(e);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/perfil/carteira/{id}")
	public ResponseEntity<DetalheContaCorrenteDto> detalharCartaoDeCredito(@PathVariable long id){
		Optional<Parceiro> parceiro = parceiroRepository.findById(id);
		if(parceiro.isPresent()) {
			Optional<ContaCorrente> cartao = contaCorrenteRepository.findById(parceiro.get().getConta().getId());
			if(cartao.isPresent()) {
				return ResponseEntity.ok(new DetalheContaCorrenteDto(cartao.get()));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PerfilParceiroDto> alterar(@RequestBody ParceiroAtualizaForm form, @PathVariable Long id){
		Optional<Parceiro> optional = parceiroRepository.findById(id);
		if(optional.isPresent()) {
			Parceiro parceiro = form.atualizar(id, parceiroRepository);
			
			return ResponseEntity.ok(new PerfilParceiroDto(parceiro));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Optional<Parceiro> parceiro = parceiroRepository.findById(id);
		if(parceiro.isPresent()) {
			parceiroRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/perfil/carteira/{id}")
	@Transactional
	public ResponseEntity<DetalheContaCorrenteDto> alterarCartao(@PathVariable Long id, 
									@RequestBody contaCorrenteAtualizacaoForm form) {
		Optional<Parceiro> optional = parceiroRepository.findById(id);
		if(optional.isPresent()) {
			ContaCorrente cartao = form.atualiza(optional.get(), contaCorrenteRepository);
			return ResponseEntity.ok(new DetalheContaCorrenteDto(cartao));
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping("/esquecisenha")
	public ResponseEntity<?> esqueciASenha(@RequestBody EsqueciASenhaForm email){
		Optional<Parceiro> parceiro = parceiroRepository.findByEmail(email.getEmail());
		if(parceiro.isPresent()) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
