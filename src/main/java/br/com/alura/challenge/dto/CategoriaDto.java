package br.com.alura.challenge.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.challenge.model.Categoria;

public class CategoriaDto {

	private Long id;
	private String titulo;
	private String cor;

	public CategoriaDto(Categoria categoria) {
		this.id = categoria.getId();
		this.titulo = categoria.getTitulo();
		this.cor = categoria.getCor();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getCor() {
		return cor;
	}

	public static List<CategoriaDto> converter(List<Categoria> categorias) {
		return categorias.stream().map(CategoriaDto::new).collect(Collectors.toList());
	}

}
