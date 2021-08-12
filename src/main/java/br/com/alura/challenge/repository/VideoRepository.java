package br.com.alura.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.challenge.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

	List<Video> findByTitulo(String titulo);

	List<Video> findByCategoriaId(Long id);

}
