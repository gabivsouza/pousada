package decorator;

public class Spa extends ServicoExtra {

	private static final double VALOR_FIXO = 120.0;

	public Spa(ItemReserva item) {
		super(item);
	}

	@Override
	protected String getNomeServico() {
		return "Spa";
	}

	@Override
	protected double getCustoServico() {
		return VALOR_FIXO;
	}

}
