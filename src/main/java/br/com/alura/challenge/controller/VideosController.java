package br.com.alura.challenge.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.challenge.dto.StatusDTO;
import br.com.alura.challenge.dto.VideoDto;
import br.com.alura.challenge.dto.VideoFormDto;
import br.com.alura.challenge.model.Video;
import br.com.alura.challenge.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideosController {

	@Autowired
	private VideoRepository videoRepository;
	
	@GetMapping
	public List<VideoDto> listar(){
		List<Video> videos = videoRepository.findAll();
		return VideoDto.converter(videos);
	}
	
	@GetMapping("/{id}")
	public VideoDto detalhar(@PathVariable(value = "id") long id){
		Video video = videoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vídeo não encontrado."));
		return new VideoDto(video);
	}	
	
	@PostMapping
	@Transactional
	public ResponseEntity<VideoDto> cadastrar(@Valid @RequestBody VideoFormDto form, UriComponentsBuilder uriBuilder) {
		Video video = form.converter();
		videoRepository.save(video);
		URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(new VideoDto(video));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<VideoDto> atualizar(@PathVariable Long id, @Valid @RequestBody VideoFormDto form){
		Optional<Video> optional = videoRepository.findById(id);
		if(optional.isPresent()) {
			Video video = form.atualizar(id, videoRepository);
			return ResponseEntity.ok(new VideoDto(video));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id){
		Optional<Video> optional = videoRepository.findById(id);
		if(optional.isPresent()) {
			videoRepository.delete(optional.get());
			return ResponseEntity.status(HttpStatus.OK).body(new StatusDTO("Excluído com sucesso."));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusDTO("Erro ao excluir."));
	}
	
}
