package uy.edu.ort.malapata.modelo;

public class Dividendo {

    private double valor;
    private boolean valido;

    public Dividendo() {
        this.valor = 0;
        this.valido = false;
    }

    public double getValor() {
        return valor;
    }

    public boolean isValido() {
        return valido;
    }

    public void recalcular(double totalCarrera, double totalAlCaballo, double comision) {
        if (totalAlCaballo == 0) {
            valido = false;
            return;
        }
        double pozoRepartir = totalCarrera * (1 - comision / 100.0);
        valor = pozoRepartir / totalAlCaballo;
        valido = valor > 1;
    }

    public void invalidar() {
        valido = false;
    }
}