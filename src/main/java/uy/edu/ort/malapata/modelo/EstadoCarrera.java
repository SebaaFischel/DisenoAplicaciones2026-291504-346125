package uy.edu.ort.malapata.modelo;

public class EstadoCarrera {

    public static final String DEFINIDA = "DEFINIDA";
    public static final String ABIERTA = "ABIERTA";
    public static final String ESTABLE = "ESTABLE";
    public static final String CERRADA = "CERRADA";
    public static final String FINALIZADA = "FINALIZADA";

    private String estado;

    public EstadoCarrera(String valor) {
        this.estado = valor;
    }

    public String getValor() {
        return estado;
    }

    public boolean esAbierta() {
    return estado.equals(ABIERTA);
}

public boolean esEstable() {
    return estado.equals(ESTABLE);
}

    public boolean puedeAbrirse() {
        return estado.equals(DEFINIDA);
    }

    public boolean puedeFinalizarse() {
        return estado.equals(CERRADA);
    }

    public boolean puedeCerrarse() {
        return estado.equals(ESTABLE);
    }

     public boolean esFinalizada() {
        return estado.equals(FINALIZADA);
    }

     public boolean esFinalOCerrada() {
        return estado.equals(CERRADA) || estado.equals(FINALIZADA);
    }

    public boolean permiteApuestas() {
        return estado.equals(ABIERTA) || estado.equals(ESTABLE);
    }

    @Override
    public String toString() {
        return estado;
    }
}