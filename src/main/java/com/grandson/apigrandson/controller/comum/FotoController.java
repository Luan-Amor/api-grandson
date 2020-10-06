package com.grandson.apigrandson.controller.comum;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grandson.apigrandson.models.Foto;
import com.grandson.apigrandson.repository.FotoRepository;

@RequestMapping("/foto")
@RestController
public class FotoController {

	@Autowired
	private FotoRepository fotoRepository;
	
	@PostMapping("/upload")
	public Foto salvar(@RequestParam("file") MultipartFile foto) throws IOException {
		String nomeArquivo = StringUtils.cleanPath(foto.getOriginalFilename());
		Foto novaFoto = new Foto(nomeArquivo, foto.getContentType(), foto.getBytes());
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
