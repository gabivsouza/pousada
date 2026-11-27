package estado;

public class Finalizada extends EstadoReserva {

	@Override
	public String getNome() {
		return "Finalizada";
	}

	@Override
	public boolean ocupaQuarto() {
		return false;
	}

}
