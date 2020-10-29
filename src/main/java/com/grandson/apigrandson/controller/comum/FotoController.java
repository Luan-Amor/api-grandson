package com.grandson.apigrandson.controller.comum;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grandson.apigrandson.config.security.TokenService;
import com.grandson.apigrandson.controller.comum.dto.FotoDto;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.FotoRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

@RequestMapping("api/foto")
@RestController
public class FotoController {

	@Autowired
	private FotoRepository fotoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/parceiro/{id}")
	@Transactional
	public ResponseEntity<FotoDto> salvarFotoParceiro(@RequestParam("file") MultipartFile foto, @PathVariable Long id) throws IOException {
		
		Optional<Parceiro> parceiro = parceiroRepository.findById(id);
		if(parceiro.isPresent()) {
			String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
			Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
			Foto save = fotoRepository.save(novaFoto);
			parceiro.get().setFoto(save);
			return ResponseEntity.ok(new FotoDto(save));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/cliente/{id}")
	@Transactional
	public ResponseEntity<FotoDto> salvarFotoCliente(@RequestParam("file") MultipartFile foto, @PathVariable Long id) throws IOException {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
			Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
			Foto save = fotoRepository.save(novaFoto);
			cliente.get().setFoto(save);
			return ResponseEntity.ok(new FotoDto(save));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/cliente")
	public ResponseEntity<FotoDto> getFotoCliente(HttpServletRequest request) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			Optional<Cliente> optional = clienteRepository.findById(id);
			if(optional.isPresent()) {
				Foto foto = optional.get().getFoto();
				return ResponseEntity.ok(new FotoDto(foto));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/parceiro")
	public ResponseEntity<FotoDto> getFotoParceiro(HttpServletRequest request) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			Optional<Parceiro> parceiro = parceiroRepository.findById(id);
			if(parceiro.isPresent()) {
				Foto foto = parceiro.get().getFoto();
				return ResponseEntity.ok(new FotoDto(foto));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/parceiro")
	@Transactional
	public ResponseEntity<FotoDto> alterarFotoParceiro(@RequestParam("file") MultipartFile foto, HttpServletRequest request) throws IOException {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {			
			Long id = tokenService.getIdUsuario(token);
			Optional<Parceiro> optional = parceiroRepository.findById(id);
			if(optional.isPresent()) {
				String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
				Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
				if(optional.get().getFoto() == null) {
					Foto save = fotoRepository.save(novaFoto);
					optional.get().setFoto(save);
				}else {
					fotoRepository.delete(optional.get().getFoto());
					Foto save = fotoRepository.save(novaFoto);
					optional.get().setFoto(save);
				}
				return ResponseEntity.ok(new FotoDto(novaFoto));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/cliente")
	@Transactional
	public ResponseEntity<FotoDto> alterarFotoCliente(@RequestParam MultipartFile foto, HttpServletRequest request) throws IOException {
		
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			Optional<Cliente> optional = clienteRepository.findById(id);
			if(optional.isPresent()) {
				String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
				Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
				if(optional.get().getFoto() == null) {
					Foto save = fotoRepository.save(novaFoto);
					optional.get().setFoto(save);
				}else {
					fotoRepository.delete(optional.get().getFoto());
					Foto save = fotoRepository.save(novaFoto);
					optional.get().setFoto(save);
				}
				return ResponseEntity.ok(new FotoDto(novaFoto));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
}
