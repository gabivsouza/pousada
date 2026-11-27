package estado;

import model.Reserva;

public class CheckInRealizado extends EstadoReserva {

	@Override
	public String getNome() {
		return "Check-in realizado";
	}

	@Override
	public void fazerCheckOut(Reserva r) {
		r.setEstado(new Finalizada());
	}

}
