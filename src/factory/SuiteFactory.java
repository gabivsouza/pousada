package factory;

import model.Quarto;
import model.Suite;

public class SuiteFactory extends QuartoFactory {
	
	@Override
	protected Quarto criarQuarto(int numero) {
		return new Suite(numero);
	}

}
