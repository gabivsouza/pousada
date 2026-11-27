package model;

public class QuartoStandard extends Quarto {
	
	public QuartoStandard(int numero) {
        super(numero);
    }

    @Override
    public String getTipo() { return "Standard"; }

    @Override
    public int getCapacidade() { return 2; }

    @Override
    public double getValorBase() { return 150.0; }

}
