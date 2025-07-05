package dados;



import java.time.LocalDate;


public class Gasto {
	private static int contador = 0;
	private int id = 0;
	private String nome;
	private LocalDate data;
	private float valor;
	private TipoGasto tipogasto;
	private int id_usuario;

	
	public Gasto(String nome,LocalDate data,float valor,TipoGasto tipogasto, int id_usuario){
		this.id = contador++;
		this.nome = nome;
		this.data = data;
		this.id_usuario = id_usuario;
		this.valor = valor;
		this.tipogasto = tipogasto;
	}
	


	public int getId() {
		return id;
	}



	public int getId_usuario() {
		return id_usuario;
	}



	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
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
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public TipoGasto getTipogasto() {
		return this.tipogasto;
	}
	
	

	@Override
	public String toString() {
		return "\nGasto \nid = " + id + "\nnome = " + nome + ",\ndata = " + data + ",\nvalor = " + valor
				+ ",\ntipogasto = " + tipogasto + "\n";
	}



	
	

	
	
}
