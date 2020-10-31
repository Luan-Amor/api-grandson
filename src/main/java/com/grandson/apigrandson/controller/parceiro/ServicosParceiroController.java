package com.grandson.apigrandson.controller.parceiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.grandson.apigrandson.Service.TransacaoService;
import com.grandson.apigrandson.config.security.TokenService;
import com.grandson.apigrandson.controller.comum.dto.MensagensDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicoDetalhadoClienteDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicosAgendadosDto;
import com.grandson.apigrandson.controller.parceiro.form.AvaliarClienteForm;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ComentarioRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;
import com.grandson.apigrandson.repository.ServicoRepository;

import me.pagar.model.PagarMeException;
import me.pagar.model.Transaction;

@RestController
@RequestMapping("api/parceiro/servico")
public class ServicosParceiroController {

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired 
 	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private TransacaoService transacao;
	
	
	@GetMapping("/agendados")
	public List<ServicosAgendadosDto> listarProximosServicos(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Parceiro parceiro = parceiroRepository.getOne(id);
			List<Servico> clientes = servicoRepository.findServicosStatus(parceiro, StatusServico.ACEITO);
			return ServicosAgendadosDto.converte(clientes);
		}
		return new ArrayList<ServicosAgendadosDto>(); 
	}
	
	@GetMapping("/concluidos")
	public List<ServicosAgendadosDto> listarServicosConcluidos(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Parceiro parceiro = parceiroRepository.getOne(id);
			List<Servico> clientes = servicoRepository.findServicosStatus(parceiro, StatusServico.AVALIADO, StatusServico.CONCLUIDO);
			return ServicosAgendadosDto.converte(clientes);
		}
		return new ArrayList<ServicosAgendadosDto>();
	}
	
	@GetMapping("/detalhar/{id}")
	public ResponseEntity<ServicoDetalhadoClienteDto> detalharServico(HttpServletRequest request, @PathVariable Long id){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Servico> servico = servicoRepository.findById(id);
			if(servico.isPresent()) {
				return ResponseEntity.ok(new ServicoDetalhadoClienteDto(servico.get()));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/aceitar/{idservico}")
	@Transactional
	public ResponseEntity<MensagensDto> aceitarServico(HttpServletRequest request, @PathVariable Long idservico) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Servico> servico = servicoRepository.findById(idservico);
			if(servico.isPresent()) {
				Cliente cliente = (Cliente) servico.get().getCliente();
				cliente.setQuantidadeServicos(cliente.getQuantidadeServicos() + 1);
				servico.get().setStatus(StatusServico.ACEITO);
				return ResponseEntity.ok(new MensagensDto("Serviço aceito."));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/cancelar/{id}")
	@Transactional
	public ResponseEntity<MensagensDto> cancelar(HttpServletRequest request, @PathVariable Long id, UriComponentsBuilder uriBuilder) throws PagarMeException {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Servico> optional = servicoRepository.findById(id);
			if(optional.isPresent()) {
				optional.get().setStatus(StatusServico.CANCELADO);
				Transaction t = transacao.executarExtorno(optional.get());
				return ResponseEntity.ok(new MensagensDto("Serviço cancelado com sucesso."));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/avaliar/{id}")
	@Transactional
	public ResponseEntity<?> avaliar(HttpServletRequest request, @PathVariable Long id,@RequestBody AvaliarClienteForm avaliar) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Servico> optional = servicoRepository.findById(id);
			if(optional.isPresent()) {
				try {
					avaliar.avaliar(optional.get(), parceiroRepository, clienteRepository, 
							servicoRepository, comentarioRepository);
				} catch (Exception e) {
					System.out.println(e);
				}
				return ResponseEntity.ok(new MensagensDto("Obrigado! O serviço foi com sucesso."));
			}
		}
		return ResponseEntity.badRequest().body(new MensagensDto("Não foi possível realizar a avaliação."));
	}
}
