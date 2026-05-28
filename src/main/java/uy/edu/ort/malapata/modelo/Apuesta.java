package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public class Apuesta {

    private Jugador jugador;
    private double monto;
    private ModalidadApuesta modalidad;
    private EstadoApuesta estado;
    private double dividendoAlCierre;
    private double montoCobrado;

    public Apuesta(Jugador jugador, double monto, ModalidadApuesta modalidad) {
        this.jugador = jugador;
        this.monto = monto;
        this.modalidad = modalidad;
        this.estado = new EstadoApuesta(EstadoApuesta.PENDIENTE);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public double getMonto() {
        return monto;
    }

    public ModalidadApuesta getModalidad() {
        return modalidad;
    }

    public EstadoApuesta getEstado() {
        return estado;
    }

    public double getDividendoAlCierre() {
        return dividendoAlCierre;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public boolean isLiquidada() {
        return estado.estaLiquidada();
    }

    public double calcularCosto() {
        return modalidad.calcularCosto(monto);
    }

    public double calcularPagoEstimado(double dividendoActual, double totalApostadoAlCaballo) {
        return modalidad.calcularPago(monto, dividendoActual, totalApostadoAlCaballo);
    }

    public boolean validarContra(Jugador jugador, Carrera carrera) throws MalaPataException {
        if (monto < 1)
            throw new MalaPataException("Monto inválido");

        if (!carrera.permiteApuestas())
            throw new MalaPataException("Esta carrera ya no recibe apuestas");

        double costo = calcularCosto();
        if (jugador.getSaldo() < costo)
            throw new MalaPataException("Saldo insuficiente");

        return true;
    }

    public void liquidar(double dividendoCierre, double totalApostadoAlCaballo) {
        this.dividendoAlCierre = dividendoCierre;
        double pago = modalidad.calcularPago(monto, dividendoCierre, totalApostadoAlCaballo);
        jugador.acreditarGanancia(pago);
        this.montoCobrado = pago;
        this.estado = new EstadoApuesta(EstadoApuesta.LIQUIDADA);

    }
}