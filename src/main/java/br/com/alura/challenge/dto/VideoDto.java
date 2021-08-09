package br.com.alura.challenge.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.challenge.model.Video;

public class VideoDto {

	private Long id;
	private String titulo;
	private String descricao;
	private String url;

	public VideoDto(Video video) {
		this.id = video.getId();
		this.titulo = video.getTitulo();
		this.descricao = video.getDescricao();
		this.url = video.getUrl();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUrl() {
		return url;
	}

	public static List<VideoDto> converter(List<Video> videos) {
		return videos.stream().map(VideoDto::new).collect(Collectors.toList());
	}

}
