package com.grandson.apigrandson.controller.cliente;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

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
import com.grandson.apigrandson.controller.cliente.dto.ServicoDetalhadoParceiroDto;
import com.grandson.apigrandson.controller.cliente.form.AtualizarServicoForm;
import com.grandson.apigrandson.controller.cliente.form.AvaliarServiçoParceiroForm;
import com.grandson.apigrandson.controller.cliente.form.FormNovoServico;
import com.grandson.apigrandson.controller.parceiro.dto.ServicoDisponiveisDto;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.AdministradorRepository;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ComentarioRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;
import com.grandson.apigrandson.repository.ServicoRepository;

import lombok.Getter;

@RestController
@RequestMapping("api/cliente/servico")
public class ServicoClienteController {

	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/agendados")
	public List<ServicoDisponiveisDto> listarProximosServicos(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		Long id = tokenService.getIdUsuario(token);
		
		Cliente cliente = clienteRepository.getOne(id);
		List<Servico> parceiros = servicoRepository.findServicosStatus(cliente, StatusServico.ACEITO);
		return ServicoDisponiveisDto.converte(parceiros);
	}
	
	@GetMapping("/finalizados")
	public List<ServicoDisponiveisDto> listarServicosConcluidos(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		Long id = tokenService.getIdUsuario(token);
		
		Cliente cliente = clienteRepository.getOne(id);
		List<Servico> parceiros = servicoRepository.findServicosStatus(cliente, StatusServico.FINALIZADO);
		return ServicoDisponiveisDto.converte(parceiros);
	}
	
	@GetMapping("detalhar/{id}")
 	public ResponseEntity<ServicoDetalhadoParceiroDto> detalhar(@PathVariable Long id){
		Optional<Servico> servico = servicoRepository.findById(id);
		if(servico.isPresent()) {
			return ResponseEntity.ok(new ServicoDetalhadoParceiroDto(servico.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastrar")
	@Transactional
	public ResponseEntity<ServicoDetalhadoParceiroDto> cadastrar(@RequestBody FormNovoServico form, UriComponentsBuilder uriBuilder){
		Servico servico = FormNovoServico.converte(form, parceiroRepository, clienteRepository);
		servicoRepository.save(servico);
		
		URI uri = uriBuilder.path("api/cliente/servico/detalhar/{id}").buildAndExpand(servico.getId()).toUri();
		return ResponseEntity.created(uri).body(new ServicoDetalhadoParceiroDto(servico));
	}
	
	@PutMapping("/cancelar/{id}")
	@Transactional
	public ResponseEntity<?> cancelar(@PathVariable Long id){
		Optional<Servico> servico = servicoRepository.findById(id);
		if(servico.isPresent()) {
			servico.get().setStatus(StatusServico.CANCELADO);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/avaliar/{id}")
	@Transactional
	public ResponseEntity<?> avaliarServico(@PathVariable Long id, @RequestBody AvaliarServiçoParceiroForm form){
		Optional<Servico> servico = servicoRepository.findById(id);
		if(servico.isPresent()) {
			form.avaliar(servico.get(), parceiroRepository, servicoRepository, comentarioRepository);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
