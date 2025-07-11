package dados;

import java.util.EnumMap;
import java.util.LinkedList;


public class Usuario {
	private int id;
	private String login;
	private String senha;
	private int idade;
	private String cpf;
	EnumMap<TipoGasto, LinkedList<Gasto>> contas = new EnumMap<TipoGasto, LinkedList<Gasto>>(TipoGasto.class);
	
	public Usuario(String login, String senha, int idade, String cpf){
		this.login = login;
		this.senha = senha;
		this.idade = idade;
		this.cpf = cpf;
		EnumMap<TipoGasto, LinkedList<Gasto>> contas = new EnumMap<TipoGasto, LinkedList<Gasto>>(TipoGasto.class);
		for(TipoGasto a : TipoGasto.values()) {
			contas.put(a, new LinkedList<>());
		}
		this.contas = contas;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}


	public int getId() {
		return id;
	}



	public EnumMap<TipoGasto, LinkedList<Gasto>> getContas() {
		return contas;
	}

	public void setContas(EnumMap<TipoGasto, LinkedList<Gasto>> contas) {
		this.contas = contas;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	@Override
	public String toString() {
		return "\nUsuario \n[\nlogin = " + login + ",\nsenha = " + senha + ",\nidade = "
				+ idade + ",\ncpf = " + cpf + "]";
	}
	

}
