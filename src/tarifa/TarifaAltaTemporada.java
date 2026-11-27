package tarifa;

import model.Quarto;

public class TarifaAltaTemporada implements EstrategiaTarifa {

	@Override
	public double calcularDiaria(Quarto quarto) {
		return quarto.getValorBase() * 1.30; // 30% de acréscimo
	}

	@Override
	public String getNome() {
		return "Alta temporada (+30%)";
	}
}
