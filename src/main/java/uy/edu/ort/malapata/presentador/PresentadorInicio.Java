package uy.edu.ort.malapata.presentador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.agenda.modelo.Sesion;
import uy.edu.ort.malapata.fachada.Fachada;

@RestController

@RequestMapping("/menuAgenda")
public class PresenterMenu {
    
    private final Fachada fachada;

    public PresenterMenu(Fachada fachada) {
        this.fachada = fachada;
    }

    @GetMapping("/vistaConectada")
    public Commands inicializarVista(@SessionAttribute(name = "sesion", required=false) Sesion sesion){
        if (sesion== null) {
             return Commands.create(new Command("usuarioNoAutenticado", "loginAgenda.html"));
         }
         return Commands.create(new Command("nombreCompleto", sesion.getUsuario().getNombreCompleto()));
        
    }



}
