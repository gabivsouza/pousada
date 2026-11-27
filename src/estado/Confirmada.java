package estado;

import model.Reserva;

public class Confirmada extends EstadoReserva {
	
	@Override
	public String getNome() { return "Confirmada"; }

	@Override
	public void fazerCheckIn(Reserva r) { r.setEstado(new CheckInRealizado()); }

	@Override
	public void cancelar(Reserva r) { r.setEstado(new Cancelada()); }

}
