package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import tarifa.EstrategiaTarifa;
import decorator.ItemReserva;
import estado.EstadoReserva;
import estado.Pendente;

public class Reserva implements ItemReserva {

	private static int proximoId = 1;

	private final int id;
	private Hospede hospede;
	private Quarto quarto;
	private LocalDate checkIn;
	private LocalDate checkOut;

	private EstrategiaTarifa tarifa;

	private EstadoReserva estado = new Pendente(); // toda reserva nasce Pendente

	public Reserva(Hospede hospede, Quarto quarto, LocalDate checkIn, LocalDate checkOut, EstrategiaTarifa tarifa) {
		if (!checkOut.isAfter(checkIn)) {
			throw new IllegalArgumentException("Check-out deve ser depois do check-in.");
		}
		this.id = proximoId++;
		this.hospede = hospede;
		this.quarto = quarto;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.tarifa = tarifa;
	}

	public int getId() {
		return id;
	}

	public Hospede getHospede() {
		return hospede;
	}

	public Quarto getQuarto() {
		return quarto;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public void confirmar() {
		estado.confirmar(this);
	}

	public void fazerCheckIn() {
		estado.fazerCheckIn(this);
	}

	public void fazerCheckOut() {
		estado.fazerCheckOut(this);
	}

	public void cancelar() {
		estado.cancelar(this);
	}

	@Override
	public long getNoites() {
		return ChronoUnit.DAYS.between(checkIn, checkOut);
	}

	public EstrategiaTarifa getTarifa() {
		return tarifa;
	}

	public void setTarifa(EstrategiaTarifa tarifa) {
		this.tarifa = tarifa;
	}

	@Override
	public double calcularTotal() {
		return tarifa.calcularDiaria(quarto) * getNoites();
	}

	@Override
	public String getDescricao() {
		return "Reserva #" + id + " - Quarto " + quarto.getNumero() + " (" + quarto.getTipo() + ") - " + getNoites()
				+ " noites";
	}

}
