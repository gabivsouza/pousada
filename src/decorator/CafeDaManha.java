package decorator;

public class CafeDaManha extends ServicoExtra {

	private static final double VALOR_POR_NOITE = 25.0;

	public CafeDaManha(ItemReserva item) {
		super(item);
	}

	@Override
	protected String getNomeServico() {
		return "Café da manhã";
	}

	@Override
	protected double getCustoServico() {
		return VALOR_POR_NOITE * item.getNoites(); // cobrado por noite
	}

}
