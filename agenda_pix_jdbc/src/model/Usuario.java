package model;

import java.util.List;



public class Usuario {
	
	int id;
	String nome;
	Chave chavePIX;
	List<Usuario> contatos;
	
	
	
	public Usuario(String nome, Chave chavePIX) {
		this.nome = nome;
		this.chavePIX = chavePIX;
	}


	public Usuario() {}

//    public boolean CriarChave(String valor, Enum_chave tipo) {
//    	Chave chave = new Chave();
//    	chave.setTipo(tipo);
//    	if (chave.setChave(valor)) {
//			return true;
//		}
//    	return false;
//    }


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Chave getChavePIX() {
		return this.chavePIX;
	}
	public void setChavePIX(Chave chavePIX) {
		this.chavePIX = chavePIX;
	}
	
	
	
	
	
	

}