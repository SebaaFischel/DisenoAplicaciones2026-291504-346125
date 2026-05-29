package uy.edu.ort.malapata.presentador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.agenda.modelo.Sesion;
import uy.edu.ort.malapata.fachada.Fachada;

@Controller
@Scope("session")
public class HomePresenter {

    private final Fachada fachada;

    public HomePresenter(Fachada fachada) {
        this.fachada = fachada;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }
}

