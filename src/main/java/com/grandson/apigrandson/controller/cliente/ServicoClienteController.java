package com.grandson.apigrandson.controller.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.grandson.apigrandson.Service.TransacaoService;
import com.grandson.apigrandson.config.security.TokenService;
import com.grandson.apigrandson.controller.cliente.dto.ServicoDetalhadoParceiroDto;
import com.grandson.apigrandson.controller.cliente.dto.ServicosConcluidosClienteDto;
import com.grandson.apigrandson.controller.cliente.dto.ServicosDisponiveisClienteDto;
import com.grandson.apigrandson.controller.cliente.form.AvaliarServiçoParceiroForm;
import com.grandson.apigrandson.controller.cliente.form.FormNovoServico;
import com.grandson.apigrandson.controller.cliente.form.MotivoCancelamentoForm;
import com.grandson.apigrandson.controller.comum.dto.MensagensDto;
import com.grandson.apigrandson.controller.parceiro.dto.ServicoDisponiveisParceiroDto;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Servico;
import com.grandson.apigrandson.models.StatusServico;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.ComentarioRepository;
import com.grandson.apigrandson.repository.EnderecoRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;
import com.grandson.apigrandson.repository.ServicoRepository;

import me.pagar.model.PagarMeException;
import me.pagar.model.Transaction;
import me.pagar.model.Transaction.Status;


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
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepositoy;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private TransacaoService transacao;
	
	@GetMapping("/agendados")
	public List<ServicosDisponiveisClienteDto> listarProximosServicos(HttpServletRequest request){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Cliente cliente = clienteRepository.getOne(id);
			List<Servico> parceiros = servicoRepository.findServicosStatus(cliente, StatusServico.ACEITO);
			return ServicosDisponiveisClienteDto.converte(parceiros);
		}
		return null;
	}
	
	@GetMapping("/concluidos")
	public List<ServicosConcluidosClienteDto> listarServicosConcluidos(HttpServletRequest request){
		List<ServicosConcluidosClienteDto> convertido = new ArrayList<ServicosConcluidosClienteDto>();
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			
			Cliente cliente = clienteRepository.getOne(id);
			List<Servico> parceiros = servicoRepository.findServicosStatus(cliente, StatusServico.CONCLUIDO);
			convertido = ServicosConcluidosClienteDto.converte(parceiros);
			return convertido;
		}
		return convertido;
	}
	
	@GetMapping("detalhar/{id}")
 	public ResponseEntity<ServicoDetalhadoParceiroDto> detalhar(HttpServletRequest request, @PathVariable Long id){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Servico> servico = servicoRepository.findById(id);
			if(servico.isPresent()) {
				return ResponseEntity.ok(new ServicoDetalhadoParceiroDto(servico.get()));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastrar")
	@Transactional
	public ResponseEntity<MensagensDto> cadastrar(HttpServletRequest request, @RequestBody FormNovoServico form) throws PagarMeException{
		String token = tokenService.recuperarToken(request);
		Long id = tokenService.getIdUsuario(token);
		
		if(tokenService.isTokenValido(token)){
			Optional<Cliente> optional = clienteRepository.findById(id);
			if(optional.isPresent()) {
				Servico servico = FormNovoServico.converte(form, parceiroRepository, 
						optional.get(), enderecoRepositoy);
				if(servico != null) {
					servicoRepository.save(servico);
					Transaction t = transacao.executarTransacao(servico);
					servico.setIdtransacao(t.getId());
					if(t.getStatus() == Status.REFUSED) {
						t.getRefuseReason();
					}
					return ResponseEntity.ok(new MensagensDto("Serviço cadastrado com sucesso.", servico.getId()));
				}
			}
		}
		return ResponseEntity.badRequest().body(new MensagensDto("Erro ao cadastrar o serviço."));
	}
	
	@PutMapping("/cancelar/{id}")
	@Transactional
	public ResponseEntity<MensagensDto> cancelar(HttpServletRequest request, 
								@PathVariable Long id, @RequestBody MotivoCancelamentoForm form) throws PagarMeException{
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Servico> servico = servicoRepository.findById(id);
			if(servico.isPresent()) {
				servico.get().setStatus(StatusServico.CANCELADO);
				servico.get().setMotivoCancelamento(form.getMotivo());
				transacao.executarExtorno(servico.get());
				return ResponseEntity.ok(new MensagensDto("Serviço cancelado com sucesso."));
			}
		}
		return ResponseEntity.badRequest().body(new MensagensDto("Falha ao cancelar o serviço."));
	}
	
	@PutMapping("/avaliar/{id}")
	@Transactional
	public ResponseEntity<?> avaliarServico(HttpServletRequest request, @PathVariable Long id, @RequestBody AvaliarServiçoParceiroForm form){
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Optional<Servico> servico = servicoRepository.findById(id);
			if(servico.isPresent()) {
				form.avaliar(servico.get(), parceiroRepository, servicoRepository, comentarioRepository);
				return ResponseEntity.ok(new MensagensDto("Obrigado! O serviço foi avaliado com sucesso."));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
}
