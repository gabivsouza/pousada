package estado;

import model.Reserva;

public abstract class EstadoReserva {

	public abstract String getNome();

	public void confirmar(Reserva r) {
		negar("confirmar");
	}

	public void fazerCheckIn(Reserva r) {
		negar("fazer check-in");
	}

	public void fazerCheckOut(Reserva r) {
		negar("fazer check-out");
	}

	public void cancelar(Reserva r) {
		negar("cancelar");
	}

	public boolean ocupaQuarto() {
		return true;
	}

	public boolean aguardaPagamento() {
		return false;
	}

	private void negar(String acao) {
		throw new IllegalStateException("Não é possível " + acao + " com a reserva " + getNome() + ".");
	}

}
