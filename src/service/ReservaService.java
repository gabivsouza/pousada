package service;

import java.time.LocalDate;
import java.util.*;
import decorator.*;
import factory.*;
import model.*;
import pagamento.ProcessadorPagamento;
import repository.Repository;
import tarifa.SeletorTarifa;

public class ReservaService {

	private Repository repository = new Repository();
	private SeletorTarifa seletor = new SeletorTarifa();

	private Map<Integer, ItemReserva> contas = new HashMap<>();

	public ReservaService() {
		carregarQuartos();
	}

	// Factory Method
	private void carregarQuartos() {
		QuartoFactory standard = new QuartoStandardFactory();
		QuartoFactory luxo = new QuartoLuxoFactory();
		QuartoFactory suite = new SuiteFactory();

		repository.salvar(standard.novoQuarto(101));
		repository.salvar(standard.novoQuarto(102));
		repository.salvar(luxo.novoQuarto(201));
		repository.salvar(luxo.novoQuarto(202));
		repository.salvar(suite.novoQuarto(301));
	}

	// Builder
	public Hospede cadastrarHospede(String nome, String cpf, String email, String telefone) {
		if (repository.buscarHospede(cpf) != null) {
			throw new IllegalArgumentException("Já existe hóspede com esse CPF.");
		}
		Hospede.Builder builder = new Hospede.Builder(nome, cpf);
		if (!email.isBlank())
			builder.email(email);
		if (!telefone.isBlank())
			builder.telefone(telefone);

		Hospede hospede = builder.build();
		repository.salvar(hospede);
		return hospede;
	}

	public List<Quarto> quartosDisponiveis(LocalDate in, LocalDate out) {
		if (!out.isAfter(in)) {
			throw new IllegalArgumentException("Check-out deve ser depois do check-in.");
		}
		List<Quarto> livres = new ArrayList<>();
		for (Quarto q : repository.todosQuartos()) {
			if (estaDisponivel(q, in, out))
				livres.add(q);
		}
		return livres;
	}

	// State
	private boolean estaDisponivel(Quarto quarto, LocalDate in, LocalDate out) {
		for (Reserva r : repository.todasReservas()) {
			boolean mesmoQuarto = r.getQuarto().getNumero() == quarto.getNumero();
			boolean ocupa = r.getEstado().ocupaQuarto();
			boolean datasSeCruzam = in.isBefore(r.getCheckOut()) && out.isAfter(r.getCheckIn());
			if (mesmoQuarto && ocupa && datasSeCruzam)
				return false;
		}
		return true;
	}

	// Strategy
	public Reserva reservar(String cpf, int numeroQuarto, LocalDate in, LocalDate out) {
		Hospede hospede = repository.buscarHospede(cpf);
		if (hospede == null)
			throw new IllegalArgumentException("Hóspede não cadastrado.");

		Quarto quarto = repository.buscarQuarto(numeroQuarto);
		if (quarto == null)
			throw new IllegalArgumentException("Quarto não existe.");

		if (!estaDisponivel(quarto, in, out)) {
			throw new IllegalArgumentException("Quarto indisponível nesse período.");
		}

		Reserva reserva = new Reserva(hospede, quarto, in, out, seletor.selecionar(in));
		repository.salvar(reserva);
		contas.put(reserva.getId(), reserva); // a conta começa só com a hospedagem
		return reserva;
	}

	// Decorator
	public void escolherExtras(int idReserva, boolean cafe, boolean translado, boolean spa) {
		Reserva reserva = buscarReserva(idReserva);
		if (!reserva.getEstado().aguardaPagamento()) {
			throw new IllegalStateException("Extras só podem ser escolhidos antes do pagamento.");
		}

		ItemReserva conta = reserva;
		if (cafe)
			conta = new CafeDaManha(conta);
		if (translado)
			conta = new Translado(conta);
		if (spa)
			conta = new Spa(conta);
		contas.put(idReserva, conta);
	}

	// Adapter e State
	public boolean pagar(int idReserva, ProcessadorPagamento pagamento) {
		Reserva reserva = buscarReserva(idReserva);
		if (!reserva.getEstado().aguardaPagamento()) {
			throw new IllegalStateException("Esta reserva não está aguardando pagamento.");
		}

		boolean aprovado = pagamento.pagar(contas.get(idReserva).calcularTotal());
		if (aprovado)
			reserva.confirmar();
		return aprovado;
	}

	public Reserva buscarReserva(int id) {
		Reserva reserva = repository.buscarReserva(id);
		if (reserva == null)
			throw new IllegalArgumentException("Reserva não encontrada.");
		return reserva;
	}

	public ItemReserva getConta(int idReserva) {
		buscarReserva(idReserva);
		return contas.get(idReserva);
	}

	public void fazerCheckIn(int id) {
		buscarReserva(id).fazerCheckIn();
	}

	public void fazerCheckOut(int id) {
		buscarReserva(id).fazerCheckOut();
	}

	public void cancelar(int id) {
		buscarReserva(id).cancelar();
	}

	public Collection<Reserva> listarReservas() {
		return repository.todasReservas();
	}

}
