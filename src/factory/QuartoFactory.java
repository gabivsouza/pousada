package factory;

import model.Quarto;

public abstract class QuartoFactory {
	
	// FACTORY METHOD: cada subclasse decide qual Quarto concreto instanciar
    protected abstract Quarto criarQuarto(int numero);

    // Lógica comum a todos os tipos (o cliente chama este método)
    public final Quarto novoQuarto(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número do quarto deve ser positivo.");
        }
        Quarto quarto = criarQuarto(numero);
        System.out.println("Criado: " + quarto);
        return quarto;
    }

}
