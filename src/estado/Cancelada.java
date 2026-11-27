package estado;

public class Cancelada extends EstadoReserva {

	@Override
	public String getNome() {
		return "Cancelada";
	}

	@Override
	public boolean ocupaQuarto() {
		return false;
	}

}
