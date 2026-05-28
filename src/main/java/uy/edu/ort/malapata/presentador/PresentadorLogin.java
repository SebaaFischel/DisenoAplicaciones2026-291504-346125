package uy.edu.ort.malapata.presentador;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.fachada.Fachada;
import uy.edu.ort.malapata.modelo.*;

@RestController
@Scope("session")
@RequestMapping("/login")
public class PresentadorLogin {

    private final Fachada fachada;

    public PresentadorLogin(Fachada fachada) {
        this.fachada = fachada;
    }

    @PostMapping("/vistaConectada")
    public Commands vistaConectada() {
        return Commands.create(new Command("mostrarLogin", true));
    }

    @PostMapping("/login")
    public Commands login(HttpSession sesionHttp,
                          @RequestParam String usuario,
                          @RequestParam String contrasena) throws MalaPataException {
        Jugador jugador = fachada.loginJugador(usuario, contrasena);
        sesionHttp.setAttribute("jugador", jugador);
        return Commands.create(new Command("loginExitoso", "jugador.html"));
    }

    @PostMapping("/logout")
    public Commands logout(HttpSession sesionHttp) {
        Jugador jugador = (Jugador) sesionHttp.getAttribute("jugador");
        if (jugador != null) {
            fachada.logoutJugador(jugador.getUsuario());
            sesionHttp.removeAttribute("jugador");
            sesionHttp.invalidate();
        }
        return Commands.create(new Command("usuarioNoAutenticado", "index.html"));
    }

    @PostMapping("/loginAdmin")
    public Commands loginAdmin(HttpSession sesionHttp,
                               @RequestParam String nombre,
                               @RequestParam String contrasenia) throws MalaPataException {
        Administrador admin = fachada.loginAdministrador(nombre, contrasenia);
        sesionHttp.setAttribute("usuarioAdmin", admin);
        return Commands.create(new Command("loginExitoso", "admin.html"));
    }

    @PostMapping("/logoutAdmin")
    public Commands logoutAdmin(HttpSession sesionHttp) {
        Administrador admin = (Administrador) sesionHttp.getAttribute("usuarioAdmin");
        if (admin != null) {
            fachada.logoutAdministrador(admin.getUsuario());
            sesionHttp.removeAttribute("usuarioAdmin");
            sesionHttp.invalidate();
        }
        return Commands.create(new Command("usuarioNoAutenticado", "index.html"));
    }
}