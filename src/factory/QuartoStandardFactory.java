package factory;

import model.Quarto;
import model.QuartoStandard;

public class QuartoStandardFactory extends QuartoFactory {
	
	@Override
    protected Quarto criarQuarto(int numero) {
        return new QuartoStandard(numero);
    }

}
