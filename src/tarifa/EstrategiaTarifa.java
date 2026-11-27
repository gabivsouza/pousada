package tarifa;

import model.Quarto;

public interface EstrategiaTarifa {
	
	// Valor de UMA noite no quarto, já com a regra da tarifa aplicada
    double calcularDiaria(Quarto quarto);

    String getNome();

}
