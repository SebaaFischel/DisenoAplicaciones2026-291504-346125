package uy.edu.ort.malapata.sistemas;

import java.time.LocalDate;
import java.util.ArrayList;
import uy.edu.ort.malapata.dto.CarreraDto;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.modelo.Carrera;
import uy.edu.ort.malapata.modelo.Jornada;
import uy.edu.ort.malapata.modelo.Participacion;

public class SistemaCarreras {

    private ArrayList<Jornada> jornadas = new ArrayList<>();

    public SistemaCarreras() {}

    public ArrayList<Jornada> getJornadas() { return jornadas; }

    public Jornada crearJornada(LocalDate fecha) {
        Jornada jornada = new Jornada(fecha);
        jornadas.add(jornada);
        return jornada;
    }

    public Jornada getJornadaActual() {
        LocalDate hoy = LocalDate.now();
        Jornada candidata = null;
        for (Jornada j : jornadas) {
            if (!j.getFecha().isAfter(hoy)) {
                if (candidata == null || j.getFecha().isAfter(candidata.getFecha()))
                    candidata = j;
            }
        }
        return candidata;
    }

    public Jornada getJornadaAnterior(Jornada actual) {
        Jornada anterior = null;
        for (Jornada j : jornadas) {
            if (j.getFecha().isBefore(actual.getFecha())) {
                if (anterior == null || j.getFecha().isAfter(anterior.getFecha()))
                    anterior = j;
            }
        }
        return anterior;
    }

    public Jornada getJornadaSiguiente(Jornada actual) {
        Jornada siguiente = null;
        for (Jornada j : jornadas) {
            if (j.getFecha().isAfter(actual.getFecha())) {
                if (siguiente == null || j.getFecha().isBefore(siguiente.getFecha()))
                    siguiente = j;
            }
        }
        return siguiente;
    }

    public int getIdJornada(Jornada jornada) {
        for (int i = 0; i < jornadas.size(); i++) {
            if (jornadas.get(i) == jornada) return i;
        }
        return -1;
    }

    public Carrera crearCarrera(Jornada jornada, String nombre) {
        int numero = jornada.proximoNumeroCarrera();
        Carrera carrera = new Carrera(numero, nombre);
        jornada.agregarCarrera(carrera);
        return carrera;
    }

    public void agregarParticipacion(Carrera carrera, Participacion participacion) {
        carrera.agregarParticipacion(participacion);
    }

    public Carrera buscarCarrera(Jornada jornada, int numero) {
        return jornada.buscarCarrera(numero);
    }

    public void abrirCarrera(Jornada jornada, int numeroCarrera) throws MalaPataException {
        obtenerCarrera(jornada, numeroCarrera).abrir();
    }

    public void cerrarCarrera(Jornada jornada, int numeroCarrera) throws MalaPataException {
        obtenerCarrera(jornada, numeroCarrera).cerrar();
    }

    public void finalizarCarrera(Jornada jornada, int numeroCarrera, int numeroGanador) throws MalaPataException {
        obtenerCarrera(jornada, numeroCarrera).finalizar(numeroGanador);
    }

    public ArrayList<CarreraDto> getCarrerasDtoDisponiblesParaApostar(ArrayList<Jornada> todasJornadas) {
        ArrayList<CarreraDto> dtos = new ArrayList<>();
        for (int i = 0; i < todasJornadas.size(); i++)
            dtos.addAll(todasJornadas.get(i).getCarrerasDtoDisponiblesParaApostar(i));
        return dtos;
    }

    private Carrera obtenerCarrera(Jornada jornada, int numero) throws MalaPataException {
        Carrera carrera = jornada.buscarCarrera(numero);
        if (carrera == null) throw new MalaPataException("No hay carrera seleccionada.");
        return carrera;
    }

public Jornada getJornadaPorId(int idJornada) {
    return jornadas.get(idJornada);
}
}