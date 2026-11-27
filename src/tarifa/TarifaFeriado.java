package tarifa;

import model.Quarto;

public class TarifaFeriado implements EstrategiaTarifa {

	@Override
	public double calcularDiaria(Quarto quarto) {
		return quarto.getValorBase() * 1.50; // 50% de acréscimo
	}

	@Override
	public String getNome() {
		return "Feriado (+50%)";
	}

}
