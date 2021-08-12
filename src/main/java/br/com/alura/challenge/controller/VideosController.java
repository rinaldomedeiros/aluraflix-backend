package br.com.alura.challenge.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.challenge.dto.StatusDto;
import br.com.alura.challenge.dto.VideoDto;
import br.com.alura.challenge.dto.VideoFormDto;
import br.com.alura.challenge.model.Video;
import br.com.alura.challenge.repository.CategoriaRepository;
import br.com.alura.challenge.repository.VideoRepository;
import jdk.jfr.Category;

@RestController
@RequestMapping("/videos")
public class VideosController {

	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<VideoDto> listar(@RequestParam(required = false) String titulo){
		List<Video> videos = new ArrayList<>();
		if(titulo == null) {
			videos = videoRepository.findAll();
		}else {
			videos = videoRepository.findByTitulo(titulo);
			System.out.println(titulo);
		}
		return VideoDto.converter(videos);
	}

	@GetMapping("/{id}")
	public VideoDto detalhar(@PathVariable(value = "id") Long id){
		Video video = videoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vídeo não encontrado."));
		return new VideoDto(video);
	}
	
	@GetMapping("/categorias/{id}/videos")
	public ResponseEntity<?> buscarVideoPorCategoria(@PathVariable(value = "id") Long id){
		List<Video> videos = videoRepository.findByCategoriaId(id);
		if(!videos.isEmpty()) {
			return ResponseEntity.ok(VideoDto.converter(videos));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusDto("Vídeo não encontrado."));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<VideoDto> cadastrar(@Valid @RequestBody VideoFormDto form, UriComponentsBuilder uriBuilder) {
		Video video = form.converter(categoriaRepository);
		videoRepository.save(video);
		URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(new VideoDto(video));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody VideoFormDto form){
		Optional<Video> optional = videoRepository.findById(id);
		if(optional.isPresent()) {
			Video video = form.atualizar(id, videoRepository);
			return ResponseEntity.ok(new VideoDto(video));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusDto("Erro ao excluir."));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id){
		Optional<Video> optional = videoRepository.findById(id);
		if(optional.isPresent()) {
			videoRepository.delete(optional.get());
			return ResponseEntity.status(HttpStatus.OK).body(new StatusDto("Excluído com sucesso."));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusDto("Erro ao excluir."));
	}
	
}
