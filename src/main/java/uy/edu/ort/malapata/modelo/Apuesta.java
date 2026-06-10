package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.modelo.interfaces.CalculadorDescuento;
import uy.edu.ort.malapata.modelo.interfaces.CalculadorPremio;

public abstract class Apuesta {

    protected double montoApostado;
    protected double dividendoCaballo;
    private Jugador jugador;
    private EstadoApuesta estado;
    private double montoCobrado;
    private CalculadorDescuento calculadorDescuento;
    private CalculadorPremio calculadorPremio;

    public Apuesta(Jugador jugador, double montoApostado,
            CalculadorDescuento calculadorDescuento,
            CalculadorPremio calculadorPremio) {
        this.jugador = jugador;
        this.montoApostado = montoApostado;
        this.calculadorDescuento = calculadorDescuento;
        this.calculadorPremio = calculadorPremio;
        this.estado = new EstadoApuesta(EstadoApuesta.PENDIENTE);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public double getMonto() {
        return montoApostado;
    }

    public double getDividendoCaballo() {
        return dividendoCaballo;
    }

    public void setDividendoCaballo(double dividendo) {
        this.dividendoCaballo = dividendo;
    }

    public EstadoApuesta getEstado() {
        return estado;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public boolean isLiquidada() {
        return estado.estaLiquidada();
    }

    public double calcularDescuento() {
        return calculadorDescuento.calcular(montoApostado);
    }

    public double calcularPremio(double dividendo,double totalApostadoAlCaballo) {
        return calculadorPremio.calcular(montoApostado, dividendo, totalApostadoAlCaballo);
    }

    public abstract String getNombre();

    public void validar(Jugador jugador, Carrera carrera) throws MalaPataException {
        if (montoApostado < 1)
            throw new MalaPataException("Monto inválido");
        if (!carrera.permiteApuestas())
            throw new MalaPataException("Esta carrera ya no recibe apuestas");
        if (jugador.getSaldo() < calcularDescuento())
            throw new MalaPataException("Saldo insuficiente");
    }

    public void liquidar(double dividendoCierre, double totalApostadoAlCaballo) {
        this.dividendoCaballo = dividendoCierre;
        double pago = calculadorPremio.calcular(montoApostado, dividendoCierre, totalApostadoAlCaballo);
        jugador.acreditarGanancia(pago);
        this.montoCobrado = pago;
        this.estado = new EstadoApuesta(EstadoApuesta.LIQUIDADA);
    }
}