package aula;


public class Hospital {

	private String nome;
	private String endereco;
	private LatLng localizacao;
		
	public Hospital(String nome, String endereco, LatLng localizacao) {
		this.nome = nome;
		this.endereco = endereco;
		this.localizacao = localizacao;
	}	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public LatLng getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(LatLng localizacao) {
		this.localizacao = localizacao;
	}
	
	
	
	
}
