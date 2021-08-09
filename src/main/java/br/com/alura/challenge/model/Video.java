package br.com.alura.challenge.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
public class Video {

	@Id
	@SequenceGenerator(name = "video_seq", sequenceName = "video_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_seq")
	private Long id;
	private String titulo;
	private String descricao;
	private String url;

//	@ManyToOne(fetch = FetchType.LAZY)
//	private Categoria categoria;

	public Video() {
	}
	
	public Video(String titulo, String descricao, String url) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.url = url;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
//	public Categoria getCategoria() {
//		return categoria;
//	}
//	public void setCategoria(Categoria categoria) {
//		this.categoria = categoria;
//	}
}
