package factory;

import model.Quarto;
import model.QuartoLuxo;

public class QuartoLuxoFactory extends QuartoFactory{
	
	@Override
	protected Quarto criarQuarto(int numero) {
		return new QuartoLuxo(numero);
	}

}
