package uy.edu.ort.malapata.modelo;

public class ComisionHipodromo {

    private static ComisionHipodromo instancia;

    private double porcentaje;

    private ComisionHipodromo() {
        this.porcentaje = 10.0;
    }

    public static ComisionHipodromo getInstancia() {
        if (instancia == null)
            instancia = new ComisionHipodromo();
        return instancia;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}