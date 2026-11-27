package decorator;

public class Translado extends ServicoExtra {

	private static final double VALOR_FIXO = 80.0;

	public Translado(ItemReserva item) {
		super(item);
	}

	@Override
	protected String getNomeServico() {
		return "Translado aeroporto";
	}

	@Override
	protected double getCustoServico() {
		return VALOR_FIXO;
	}

}
