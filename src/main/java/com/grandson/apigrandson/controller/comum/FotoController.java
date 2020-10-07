package com.grandson.apigrandson.controller.comum;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

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

import com.grandson.apigrandson.controller.comum.dto.FotoDetalheDto;
import com.grandson.apigrandson.models.Cliente;
import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.models.Parceiro;
import com.grandson.apigrandson.repository.ClienteRepository;
import com.grandson.apigrandson.repository.FotoRepository;
import com.grandson.apigrandson.repository.ParceiroRepository;

@RequestMapping("/foto")
@RestController
public class FotoController {

	@Autowired
	private FotoRepository fotoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	
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
	
	@GetMapping("/{id}")
	public Foto getFoto(@PathVariable Long id) {
		return fotoRepository.getOne(id);
	}
	
	@GetMapping("/")
	public Stream<Foto> allPhotos(){
		return fotoRepository.findAll().stream();
	}
	
}
