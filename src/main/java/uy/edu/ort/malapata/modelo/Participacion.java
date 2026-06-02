package uy.edu.ort.malapata.modelo;

import java.util.ArrayList;
import uy.edu.ort.malapata.dto.ApuestaDto;

public class Participacion {

    private int numero;
    private Caballo caballo;
    private Dividendo dividendo;
    private ArrayList<Apuesta> apuestas = new ArrayList<>();

    public Participacion(int numero, Caballo caballo) {
        this.numero = numero;
        this.caballo = caballo;
        this.dividendo = new Dividendo();
    }

    public int getNumero() {
        return numero;
    }

    public Caballo getCaballo() {
        return caballo;
    }

    public Dividendo getDividendo() {
        return dividendo;
    }

    public ArrayList<Apuesta> getApuestas() {
        return apuestas;
    }

    public double getValorDividendo() {
        return dividendo.getValor();
    }

    public boolean isDividendoValido() {
        return dividendo.isValido();
    }

    public double getTotalApostadoEnParticipacion() {
        double total = 0;
        for (Apuesta a : apuestas)
            total += a.getMonto();
        return total;
    }

    public ArrayList<Apuesta> getApuestasDelJugador(String usuarioJugador) {
        ArrayList<Apuesta> resultado = new ArrayList<>();
        for (Apuesta a : apuestas) {
            if (a.getJugador().getUsuario().equals(usuarioJugador)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

public ArrayList<ApuestaDto> getApuestasDtoDelJugador(String usuarioJugador,
        String nombreCarrera,
        int numeroCarrera,
        String fechaJornada,
        String estadoCarrera) {
    ArrayList<ApuestaDto> resultado = new ArrayList<>();
    for (Apuesta a : apuestas) {
        if (a.getJugador().getUsuario().equals(usuarioJugador)) {
            resultado.add(new ApuestaDto(
                    a,
                    getCaballo().getNombre(),
                    this.numero,
                    nombreCarrera,
                    numeroCarrera,
                    fechaJornada,
                    estadoCarrera));
        }
    }
    return resultado;
}

    public int getCantidadApuestas() {
        return apuestas.size();
    }

    public void agregarApuesta(Apuesta apuesta) {
        apuestas.add(apuesta);
    }

    public void recalcularDividendo(double totalCarrera, double comision) {
        dividendo.recalcular(totalCarrera, getTotalApostadoEnParticipacion(), comision);
    }

    public void invalidarDividendo() {
        dividendo.invalidar();
    }

    public double getTotalPagado() {
    double total = 0;
    for (Apuesta a : apuestas)
        total += a.getMontoCobrado();
    return total;
}
}