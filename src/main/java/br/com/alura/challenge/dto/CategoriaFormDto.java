package br.com.alura.challenge.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.alura.challenge.model.Categoria;
import br.com.alura.challenge.repository.CategoriaRepository;


public class CategoriaFormDto {

	@NotBlank
	@Length(max = 50)
	private String titulo;

	@NotBlank
	@Length(max = 20)
	private String cor;

	public String getTitulo() {
		return titulo;
	}

	public String getCor() {
		return cor;
	}

	public Categoria converter() {
		return new Categoria(titulo, cor);
	}

	public Categoria atualizar(Long id, CategoriaRepository categoriaRepository) {
		Categoria categoria = categoriaRepository.getById(id);
		categoria.setTitulo(this.titulo);
		categoria.setCor(this.cor);
		return categoria;
	}

}
