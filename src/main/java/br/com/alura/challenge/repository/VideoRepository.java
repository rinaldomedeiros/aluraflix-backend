package br.com.alura.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.challenge.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
