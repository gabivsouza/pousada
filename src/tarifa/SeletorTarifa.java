package tarifa;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Set;

public class SeletorTarifa {

	private static final Set<MonthDay> FERIADOS = Set.of(
			MonthDay.of(1, 1), // Confraternização Universal
			MonthDay.of(2, 26), // Meu Niver
			MonthDay.of(4, 21), // Tiradentes
			MonthDay.of(5, 1), // Dia do Trabalho
			MonthDay.of(7, 2), // Independência da Bahia
			MonthDay.of(7, 3), // Niver de Mamis
			MonthDay.of(9, 7), // Independência do Brasil
			MonthDay.of(10, 12), // N. Sra. Aparecida
			MonthDay.of(11, 2), // Finados
			MonthDay.of(11, 15), // Proclamação da República
			MonthDay.of(11, 20), // Consciência Negra
			MonthDay.of(12, 8), // N. Sra. da Conceição da Praia
			MonthDay.of(12, 25) // Natal
	);
	
	private final EstrategiaTarifa baixa = new TarifaBaixaTemporada();
    private final EstrategiaTarifa alta = new TarifaAltaTemporada();
    private final EstrategiaTarifa feriado = new TarifaFeriado();

    // Decide pela data de check-in
    public EstrategiaTarifa selecionar(LocalDate data) {
        if (FERIADOS.contains(MonthDay.from(data))) {
            return feriado;
        }
        if (ehAltaTemporada(data)) {
            return alta;
        }
        return baixa;
    }

    // Verão (15/12 a 28/02) e férias de junho
    private boolean ehAltaTemporada(LocalDate data) {
        int mes = data.getMonthValue();
        if (mes == 1 || mes == 2 || mes == 6) {
            return true;
        }
        return mes == 12 && data.getDayOfMonth() >= 15;
    }

}
