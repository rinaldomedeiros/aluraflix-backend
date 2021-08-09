package br.com.alura.challenge.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.alura.challenge.model.Video;
import br.com.alura.challenge.repository.VideoRepository;

public class VideoFormDto {

	@NotBlank
	@Length(max = 50)
	private String titulo;

	@NotBlank
	@Length(max = 255)
	private String descricao;

	@NotBlank
	@Length(max = 255)
	private String url;

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUrl() {
		return url;
	}

	public Video converter() {
		return new Video(titulo, descricao, url);
	}

	public Video atualizar(Long id, VideoRepository videoRepository) {
		Video video = videoRepository.getById(id);
		video.setTitulo(this.titulo);
		video.setDescricao(this.descricao);
		video.setUrl(this.url);
		return video;
	}
	
}
