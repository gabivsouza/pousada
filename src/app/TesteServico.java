package app;

import java.time.LocalDate;
import decorator.ItemReserva;
import model.Reserva;
import pagamento.PagamentoCartaoAdapter;
import service.ReservaService;

public class TesteServico {

	public static void main(String[] args) {

		ReservaService service = new ReservaService();

		service.cadastrarHospede("Maria Silva", "123.456.789-09", "maria@email.com", "");

		LocalDate in = LocalDate.of(2026, 12, 20);
		LocalDate out = LocalDate.of(2026, 12, 25);

		System.out.println("Disponíveis: " + service.quartosDisponiveis(in, out).size());

		Reserva r = service.reservar("12345678909", 201, in, out);
		System.out.println("Estado: " + r.getEstado().getNome());

		service.escolherExtras(r.getId(), true, false, true);
		ItemReserva conta = service.getConta(r.getId());
		System.out.println(conta.getDescricao());
		System.out.printf("Total: R$ %.2f%n", conta.calcularTotal());

		System.out.println("Disponíveis agora: " + service.quartosDisponiveis(in, out).size());
		try {
			service.reservar("12345678909", 201, in, out);
		} catch (IllegalArgumentException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		boolean ok = service.pagar(r.getId(), new PagamentoCartaoAdapter());
		System.out.println("Pagou? " + ok + " | Estado: " + r.getEstado().getNome());

		try {
			service.escolherExtras(r.getId(), true, true, true);
		} catch (IllegalStateException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		// Cartão recusado: suíte por 10 noites passa de R$ 5.000
		Reserva r2 = service.reservar("12345678909", 301, in, LocalDate.of(2026, 12, 30));
		boolean ok2 = service.pagar(r2.getId(), new PagamentoCartaoAdapter());
		System.out.println("Pagou? " + ok2 + " | Estado: " + r2.getEstado().getNome());

	}

}
