package br.com.alura.challenge.config.validacao;

public class ErroDeValidacaoDto {

	private String campo;
	private String erro;
	
	public ErroDeValidacaoDto(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
}
