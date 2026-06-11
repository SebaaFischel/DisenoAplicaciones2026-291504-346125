package uy.edu.ort.malapata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uy.edu.ort.malapata.datosPrueba.DatosPrueba;
import uy.edu.ort.malapata.fachada.Fachada;

@SpringBootApplication
public class MalaPataApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MalaPataApplication.class, args);
        DatosPrueba.cargar(Fachada.getInstancia());
    }
}