package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;
import decorator.ItemReserva;
import model.Hospede;
import model.Quarto;
import model.Reserva;
import pagamento.PagamentoCartaoAdapter;
import pagamento.PagamentoPixAdapter;
import pagamento.ProcessadorPagamento;
import service.ReservaService;

public class Menu {

	private Scanner scanner = new Scanner(System.in);
	private ReservaService service = new ReservaService();
	// STRICT: recusa datas que não existem, como 31/02
	private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu")
			.withResolverStyle(ResolverStyle.STRICT);

	public void iniciar() {
		int opcao = -1;
		while (opcao != 0) {
			mostrarMenu();
			try {
				opcao = lerInt("Opção: ");
				executar(opcao);
			} catch (IllegalArgumentException | IllegalStateException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
		System.out.println("Até logo!");
	}

	private void mostrarMenu() {
		System.out.println();
		System.out.println("===== POUSADA =====");
		System.out.println("1 - Cadastrar hóspede");
		System.out.println("2 - Ver quartos disponíveis");
		System.out.println("3 - Fazer reserva");
		System.out.println("4 - Escolher extras");
		System.out.println("5 - Pagar reserva");
		System.out.println("6 - Fazer check-in");
		System.out.println("7 - Fazer check-out");
		System.out.println("8 - Cancelar reserva");
		System.out.println("9 - Listar reservas");
		System.out.println("0 - Sair");
	}

	private void executar(int opcao) {
		switch (opcao) {
		case 1:
			cadastrarHospede();
			break;
		case 2:
			verQuartos();
			break;
		case 3:
			fazerReserva();
			break;
		case 4:
			escolherExtras();
			break;
		case 5:
			pagar();
			break;
		case 6:
			checkIn();
			break;
		case 7:
			checkOut();
			break;
		case 8:
			cancelar();
			break;
		case 9:
			listarReservas();
			break;
		case 0:
			break;
		default:
			System.out.println("Opção inválida.");
		}
	}

	private void cadastrarHospede() {
		String nome = lerTexto("Nome: ");
		String cpf = lerTexto("CPF: ");
		String email = lerTexto("E-mail (Enter para pular): ");
		String telefone = lerTexto("Telefone (Enter para pular): ");

		Hospede h = service.cadastrarHospede(nome, cpf, email, telefone);
		System.out.println("Cadastrado! " + h);
	}

	private void verQuartos() {
		LocalDate in = lerData("Check-in (dd/MM/aaaa): ");
		LocalDate out = lerData("Check-out (dd/MM/aaaa): ");
		listarQuartos(in, out);
	}

	private void listarQuartos(LocalDate in, LocalDate out) {
		List<Quarto> livres = service.quartosDisponiveis(in, out);
		if (livres.isEmpty()) {
			System.out.println("Nenhum quarto disponível nesse período.");
		}
		for (Quarto q : livres) {
			System.out.println(q);
		}
	}

	private void fazerReserva() {
		String cpf = lerTexto("CPF do hóspede: ");
		LocalDate in = lerData("Check-in (dd/MM/aaaa): ");
		LocalDate out = lerData("Check-out (dd/MM/aaaa): ");
		listarQuartos(in, out);
		int numero = lerInt("Número do quarto: ");

		Reserva r = service.reservar(cpf, numero, in, out);
		System.out.println("Reserva #" + r.getId() + " criada (" + r.getEstado().getNome() + ")");
		System.out.println("Tarifa: " + r.getTarifa().getNome());
		System.out.printf("Total da hospedagem: R$ %.2f%n", r.calcularTotal());
	}

	private void escolherExtras() {
		int id = lerInt("Número da reserva: ");
		boolean cafe = lerSimNao("Café da manhã");
		boolean translado = lerSimNao("Translado aeroporto");
		boolean spa = lerSimNao("Spa");

		service.escolherExtras(id, cafe, translado, spa);
		mostrarConta(id);
	}

	private void pagar() {
		int id = lerInt("Número da reserva: ");
		mostrarConta(id);
		int metodo = lerInt("Pagamento (1 = Pix, 2 = Cartão): ");

		ProcessadorPagamento pagamento = null;
		switch (metodo) {
		case 1:
			pagamento = new PagamentoPixAdapter();
			break;
		case 2:
			pagamento = new PagamentoCartaoAdapter();
			break;
		default:
			throw new IllegalArgumentException("Método de pagamento inválido.");
		}

		boolean aprovado = service.pagar(id, pagamento);
		System.out.println(aprovado ? "Pagamento aprovado!" : "Pagamento recusado.");
		mostrarEstado(id);
	}

	private void checkIn() {
		int id = lerInt("Número da reserva: ");
		service.fazerCheckIn(id);
		mostrarEstado(id);
	}

	private void checkOut() {
		int id = lerInt("Número da reserva: ");
		service.fazerCheckOut(id);
		mostrarEstado(id);
	}

	private void cancelar() {
		int id = lerInt("Número da reserva: ");
		service.cancelar(id);
		mostrarEstado(id);
	}

	private void listarReservas() {
		if (service.listarReservas().isEmpty()) {
			System.out.println("Nenhuma reserva cadastrada.");
		}
		for (Reserva r : service.listarReservas()) {
			ItemReserva conta = service.getConta(r.getId());
			System.out
					.println("#" + r.getId() + " | " + r.getHospede().getNome() + " | " + r.getCheckIn().format(formato)
							+ " a " + r.getCheckOut().format(formato) + " | " + r.getEstado().getNome());
			System.out.println("   " + conta.getDescricao());
			System.out.printf("   Total: R$ %.2f%n", conta.calcularTotal());
		}
	}

	private void mostrarConta(int id) {
		ItemReserva conta = service.getConta(id);
		System.out.println(conta.getDescricao());
		System.out.printf("Total: R$ %.2f%n", conta.calcularTotal());
	}

	private void mostrarEstado(int id) {
		System.out.println("Reserva #" + id + " agora está: " + service.buscarReserva(id).getEstado().getNome());
	}

	private String lerTexto(String mensagem) {
		System.out.print(mensagem);
		return scanner.nextLine().trim();
	}

	private int lerInt(String mensagem) {
		try {
			return Integer.parseInt(lerTexto(mensagem));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Digite um número válido.");
		}
	}

	private LocalDate lerData(String mensagem) {
		try {
			return LocalDate.parse(lerTexto(mensagem), formato);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Data inválida. Use dd/MM/aaaa.");
		}
	}

	private boolean lerSimNao(String pergunta) {
		return lerTexto(pergunta + " (s/n): ").equalsIgnoreCase("s");
	}

}
