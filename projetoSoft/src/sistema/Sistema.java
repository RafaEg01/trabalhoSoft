package sistema;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dados.Gasto;
import dados.TipoGasto;
import dados.Usuario;


public class Sistema {
	private static Sistema instance; // Static instance of the Sistema class
	private Usuario logadoAtual = null;
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

	// Private constructor to prevent external instantiation
	private Sistema() {
		// Initialize any necessary components here if needed
	}

	// Public static method to get the single instance of Sistema
	public static Sistema getInstance() {
		if (instance == null) {
			instance = new Sistema();
		}
		return instance;
	}

	public ArrayList<Usuario> getUsuarios(){
		return usuarios;
	}

	public void setUsuarios(Usuario u) {
		this.usuarios.add(u);
	}


	public Usuario getLogadoAtual() {
		return logadoAtual;
	}


	public void logout() {
		this.logadoAtual = null;
	}


	public boolean cadastrarUsuario(Usuario u) {
		if(u != null) {
			for(Usuario t : getUsuarios()) {
				if(t.getLogin().equals( u.getLogin())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}



	public Usuario loginUsuario(String login, String senha) {
		if(login != null && senha != null) {
			for(Usuario u : getUsuarios()) {
				if(login.equals(u.getLogin()) && senha.equals(u.getSenha())) {
					this.logadoAtual = u;
					return u;
				}
			}
		}else {
			return null;
		}
		return null;

	}

	public boolean cadastrarGasto(Usuario u, Gasto g) {
		if(u != null && g != null) {
			LinkedList<Gasto> aux = new LinkedList<Gasto>();
			aux = u.getContas().get(g.getTipogasto());
			aux.add(g);
			u.getContas().put(g.getTipogasto(), aux);
			return true;
		}else {
			System.out.println("Entrada de valores Invalidas");
			return false;
		}

	}

	public LinkedList<Gasto> visualizarGasto(Usuario u) {
		LinkedList<Gasto> aux = new LinkedList<Gasto>();
		for(TipoGasto tipo : TipoGasto.values()) {
			aux.addAll(u.getContas().get(tipo));
		}
		return aux;
	}

	public void removeGasto(Usuario u,Gasto g) {
		int cont = 0;
		TipoGasto tipogasto = g.getTipogasto();
		LinkedList<Gasto> aux = new LinkedList<Gasto>();
		aux = u.getContas().get(tipogasto);
		for(Gasto a: aux) {
			if(a.getNome().equals(g.getNome())) {
				aux.remove(cont);
				u.getContas().put(tipogasto, aux);
			}
			cont++;
		}

	}
	
	public LinkedList<Gasto> visualizarGastoAnual(Usuario u, int i){
		LinkedList<Gasto> aux = new LinkedList<Gasto>();
		for(TipoGasto tipo : TipoGasto.values()) {
			aux.addAll(u.getContas().get(tipo));
		}
		LinkedList<Gasto> aux1 = new LinkedList<Gasto>();
		for(Gasto gastos : aux) {
			if(gastos.getData().getYear() == i) {
				aux1.add(gastos);
			}
		}
		return aux1;
	}

	public boolean equals(Gasto g1, Gasto g2){
		if(g1.getId() == g2.getId()) {
			return true;
		}else {
			return false;
		}

	}

	public boolean contem(List<Gasto> lista, Gasto g1) {
		for(Gasto g : lista) {
			if(g.equals(g1)) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	public Gasto buscarGastoPorId(EnumMap<TipoGasto, LinkedList<Gasto>> contas, int id) {
	    for (LinkedList<Gasto> lista : contas.values()) {
	        for (Gasto gasto : lista) {
	            if (gasto.getId() == id) {
	                return gasto;
	            }
	        }
	    }
	    return null;
	}

}