package dados;

public enum TipoGasto {
	Comida(1),
	Lazer(2),
	Educação(3),
	Saúde(4),
	Transporte(5),
	Outros(6);
	
	private int valor;

	TipoGasto(int valor) {
		this.valor = valor;
	}
	
	 public int getValor() {
	        return valor;
	    }
	

	   public static TipoGasto fromInt(int valor) {
	        for (TipoGasto tipo : TipoGasto.values()) {
	            if (tipo.getValor() == valor) {
	                return tipo;
	            }
	        }
	        throw new IllegalArgumentException("Valor inválido: " + valor);
	    }
	
	
	
	

}
