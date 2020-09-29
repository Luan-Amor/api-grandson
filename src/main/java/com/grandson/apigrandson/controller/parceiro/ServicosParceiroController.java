package com.grandson.apigrandson.controller.parceiro;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.grandson.apigrandson.controller.cliente.dto.ClienteListaDto;
import com.grandson.apigrandson.controller.cliente.dto.ListaParceirosDisponiveisDto;
import com.grandson.apigrandson.controller.cliente.dto.ServicoDetalhadoParceiroDto;
import com.grandson.apigrandson.controller.parceiro.dto.ListaParceiroDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicoDetalhadoClienteDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicosAgendadosDto;
import com.grandson.apigrandson.controller.parceiro.form.AvaliarClienteForm;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ComentarioRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;
import com.grandson.apigrandson.repository.ServicoRepository;

@RestController
@RequestMapping("api/parceiro/servico")
public class ServicosParceiroController {

	/*
	 * | URL's Servico Cliente 			|
	 * | 
	 * | Aceitar Serviço				| Detalhe do Serviço	|OK
	 * | Cancelar Serciço				| HTTP 200/404			|OK
	 * | Detalhar Serviço				| Detalhe do Serviço	|OK
	 * | Listar serviços agendados		| Lista Parceiros		|OK
	 * | Listar serviços concluídos		| Listar Parceiros		|OK
	 * | Avaliar Serviço				| HTTP 200/404			|OK
	 * 
	 * */
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired 
 	private ComentarioRepository comentarioRepository;
	
	
	@GetMapping("/agendados/{id}")
	public List<ServicosAgendadosDto> listarProximosServicos(@PathVariable Long id){
		Parceiro parceiro = parceiroRepository.getOne(id);
		List<Servico> clientes = servicoRepository.findServicosStatus(parceiro, StatusServico.ACEITO);
		return ServicosAgendadosDto.converte(clientes);
	}
	
	@GetMapping("/concluidos/{id}")
	public List<ServicosAgendadosDto> listarServicosConcluidos(@PathVariable Long id){
		Parceiro parceiro = parceiroRepository.getOne(id);
		List<Servico> clientes = servicoRepository.findServicosStatus(parceiro, StatusServico.AVALIADO, StatusServico.CONCLUIDO);
		return ServicosAgendadosDto.converte(clientes);
	}
	
	@GetMapping("/detalhar/{id}")
	public ResponseEntity<ServicoDetalhadoClienteDto> detalharServico(@PathVariable Long id){
		Optional<Servico> servico = servicoRepository.findById(id);
		if(servico.isPresent()) {
			return ResponseEntity.ok(new ServicoDetalhadoClienteDto(servico.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/aceitar/{id}")
	@Transactional
	public ResponseEntity<ServicoDetalhadoClienteDto> aceitarServico(@PathVariable Long id, UriComponentsBuilder uriBuilder) {
		Optional<Servico> servico = servicoRepository.findById(id);
		if(servico.isPresent()) {
			servico.get().setStatus(StatusServico.ACEITO);
			URI uri = uriBuilder.path("/detalhar/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(uri).body(new ServicoDetalhadoClienteDto(servico.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/cancelar/{id}")
	@Transactional
	public ResponseEntity<?> cancelar(@PathVariable Long id, UriComponentsBuilder uriBuilder) {
		Optional<Servico> optional = servicoRepository.findById(id);
		if(optional.isPresent()) {
			optional.get().setStatus(StatusServico.CANCELADO);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/avaliar/{id}")
	@Transactional
	public ResponseEntity<?> avaliar(@PathVariable Long id,@RequestBody AvaliarClienteForm avaliar) {
		Optional<Servico> optional = servicoRepository.findById(id);
		if(optional.isPresent()) {
			try {
				avaliar.avaliar(optional.get(), parceiroRepository, clienteRepository, 
						servicoRepository, comentarioRepository);
			} catch (Exception e) {
				System.out.println(e);
			}
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
