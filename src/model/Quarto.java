package model;

public class Quarto {
	private final int numero;
	private String tipo;
	private int capacidade;
	private double valorBase;
	

    protected Quarto(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }
    
    public String getTipo() {
		return tipo;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public double getValorBase() {
		return valorBase;
	}

	@Override
    public String toString() {
        return String.format("Quarto %d (%s) - capacidade %d - R$ %.2f/noite",
                numero, getTipo(), getCapacidade(), getValorBase());
    }

}
