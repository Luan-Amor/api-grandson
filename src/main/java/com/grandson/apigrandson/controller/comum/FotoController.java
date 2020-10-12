package com.grandson.apigrandson.controller.comum;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grandson.apigrandson.config.security.TokenService;
import com.grandson.apigrandson.controller.comum.dto.FotoDetalheDto;
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
	public ResponseEntity<FotoDetalheDto> salvarFotoParceiro(@RequestParam("file") MultipartFile foto, @PathVariable Long id) throws IOException {
		String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
		Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
		
		Foto save = fotoRepository.save(novaFoto);
		Optional<Parceiro> parceiro = parceiroRepository.findById(id);
		if(parceiro.isPresent()) {
			parceiro.get().setFoto(save);
		}
		return ResponseEntity.ok(new FotoDetalheDto(save));
	}
	
	@PostMapping("/cliente/{id}")
	public Foto salvarFotoCliente(@RequestParam("file") MultipartFile foto, @PathVariable Long id) throws IOException {
		String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
		Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
		
		Foto save = fotoRepository.save(novaFoto);
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			cliente.get().setFoto(save);
		}
		
		return fotoRepository.save(novaFoto);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<FotoDetalheDto> getFotoCliente(HttpServletRequest request) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			Optional<Cliente> optional = clienteRepository.findById(id);
			if(optional.isPresent()) {
				Foto foto = optional.get().getFoto();
				return ResponseEntity.ok(new FotoDetalheDto(foto));
			}
			
		}
		return ResponseEntity.badRequest().build();
	}
	@GetMapping("/parceiro/{id}")
	public ResponseEntity<FotoDetalheDto> getFotoParceiro(HttpServletRequest request) {
		String token = tokenService.recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			Long id = tokenService.getIdUsuario(token);
			Optional<Parceiro> parceiro = parceiroRepository.findById(id);
			if(parceiro.isPresent()) {
				Foto foto = parceiro.get().getFoto();
				return ResponseEntity.ok(new FotoDetalheDto(foto));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	/*Métodos de teste*/
	
	@PostMapping("/")
	public ResponseEntity<FotoDetalheDto> salvar(@RequestParam("file") MultipartFile foto) throws IOException {
		String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
		Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
		
		Foto save = fotoRepository.save(novaFoto);
		return ResponseEntity.ok(new FotoDetalheDto(save));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FotoDetalheDto> getFoto(@PathVariable Long id){ 
		Optional<Foto> optional = fotoRepository.findById(id);
		if(optional.isPresent()) {
			return ResponseEntity.ok(new FotoDetalheDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/")
	public Stream<Foto> allPhotos(){
		return fotoRepository.findAll().stream();
	}
	
}
