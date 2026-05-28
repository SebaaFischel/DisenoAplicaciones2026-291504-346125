package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public class Jugador extends Usuario {

    private double saldo;
    private double totalApostado;
    private double totalGanado;

    public Jugador(String usuario, String contrasena, String nombreCompleto, double saldo) {
        super(usuario, contrasena, nombreCompleto);
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getTotalApostado() {
        return totalApostado;
    }

    public double getTotalGanado() {
        return totalGanado;
    }

    public void descontarSaldo(double monto) throws MalaPataException {
        if (saldo < monto)
            throw new MalaPataException("Saldo insuficiente");
        saldo -= monto;
        totalApostado += monto;
    }

    public void acreditarGanancia(double ganancia) {
        saldo += ganancia;
        totalGanado += ganancia;
    }
}