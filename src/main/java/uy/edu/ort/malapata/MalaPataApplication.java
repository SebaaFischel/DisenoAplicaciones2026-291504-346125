package uy.edu.ort.malapata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uy.edu.ort.malapata.datosPrueba.DatosPrueba;
import uy.edu.ort.malapata.fachada.Fachada;

@SpringBootApplication
public class MalaPataApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(MalaPataApplication.class, args);
        Fachada fachada = context.getBean(Fachada.class);
        DatosPrueba.cargar(fachada);
    }
}