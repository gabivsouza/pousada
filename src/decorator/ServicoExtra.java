package decorator;

public abstract class ServicoExtra implements ItemReserva {

	protected final ItemReserva item;

	protected ServicoExtra(ItemReserva item) {
		this.item = item;
	}

	protected abstract String getNomeServico();

	protected abstract double getCustoServico();

	@Override
	public String getDescricao() {
		return item.getDescricao() + " + " + getNomeServico();
	}

	@Override
	public double calcularTotal() {
		return item.calcularTotal() + getCustoServico();
	}

	@Override
	public long getNoites() {
		return item.getNoites();
	}

}
