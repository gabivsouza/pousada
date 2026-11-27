package tarifa;

import model.Quarto;

public class TarifaBaixaTemporada implements EstrategiaTarifa {
	
	@Override
	public double calcularDiaria(Quarto quarto) {
		return quarto.getValorBase()*0.90; // 10% de desconto
	}
	
	@Override
	public String getNome() {
		return "Baixa temporada (-10%)";
	}

}
