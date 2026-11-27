package model;

public class Suite extends Quarto {
	
	public Suite(int numero) {
        super(numero);
    }

    @Override
    public String getTipo() { return "Suíte"; }

    @Override
    public int getCapacidade() { return 4; }

    @Override
    public double getValorBase() { return 450.0; }

}
