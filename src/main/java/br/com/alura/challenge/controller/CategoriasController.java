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

import br.com.alura.challenge.dto.CategoriaDto;
import br.com.alura.challenge.dto.CategoriaFormDto;
import br.com.alura.challenge.dto.StatusDto;
import br.com.alura.challenge.dto.VideoDto;
import br.com.alura.challenge.model.Categoria;
import br.com.alura.challenge.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@GetMapping
	public List<CategoriaDto> listar(){
		List<Categoria> categorias = categoriaRepository.findAll();
		return CategoriaDto.converter(categorias);
	}
	
	@GetMapping("/{id}")
	public CategoriaDto detalhar(@PathVariable(value = "id") Long id) {
		Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada."));
		return new CategoriaDto(categoria);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CategoriaDto> cadastrar (@Valid @RequestBody CategoriaFormDto form, UriComponentsBuilder uriBuilder){
		Categoria categoria = form.converter();
		categoriaRepository.save(categoria);
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizar (@PathVariable(value = "id") Long id, @Valid @RequestBody CategoriaFormDto form){
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if(optional.isPresent()) {
			Categoria categoria = form.atualizar(id, categoriaRepository);
			return ResponseEntity.ok(new CategoriaDto(categoria));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusDto("Categoria não encontrada."));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir (@PathVariable(value = "id") Long id){
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if(optional.isPresent()) {
			categoriaRepository.delete(optional.get());
			return ResponseEntity.status(HttpStatus.OK).body(new StatusDto("Excluído com sucesso."));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusDto("Erro ao excluir."));
	}
	
	
}
