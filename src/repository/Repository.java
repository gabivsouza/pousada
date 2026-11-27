package repository;

import java.util.*;
import model.*;

public class Repository {

	private Map<String, Hospede> hospedes = new LinkedHashMap<>();
	private Map<Integer, Quarto> quartos = new LinkedHashMap<>();
	private Map<Integer, Reserva> reservas = new LinkedHashMap<>();

	private String soDigitos(String cpf) {
		return cpf.replaceAll("\\D", "");
	}

	// Sobrecarga: mesmo nome, parâmetros diferentes
	public void salvar(Hospede h) {
		hospedes.put(soDigitos(h.getCpf()), h);
	}

	public void salvar(Quarto q) {
		quartos.put(q.getNumero(), q);
	}

	public void salvar(Reserva r) {
		reservas.put(r.getId(), r);
	}

	public Hospede buscarHospede(String cpf) {
		return hospedes.get(soDigitos(cpf));
	}

	public Quarto buscarQuarto(int numero) {
		return quartos.get(numero);
	}

	public Reserva buscarReserva(int id) {
		return reservas.get(id);
	}

	public Collection<Quarto> todosQuartos() {
		return quartos.values();
	}

	public Collection<Reserva> todasReservas() {
		return reservas.values();
	}

}
