package model;

public class QuartoLuxo extends Quarto {
	
	public QuartoLuxo(int numero) {
        super(numero);
    }

    @Override
    public String getTipo() { return "Luxo"; }

    @Override
    public int getCapacidade() { return 3; }

    @Override
    public double getValorBase() { return 280.0; }

}
