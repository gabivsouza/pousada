package estado;

import model.Reserva;

public class Pendente extends EstadoReserva {

	@Override
	public String getNome() {
		return "Pendente";
	}

	@Override
	public void confirmar(Reserva r) {
		r.setEstado(new Confirmada());
	}

	@Override
	public void cancelar(Reserva r) {
		r.setEstado(new Cancelada());
	}

	@Override
	public boolean aguardaPagamento() {
		return true;
	}
}
