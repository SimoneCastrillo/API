package buffet.app_web.controllers;

import buffet.app_web.config.Google;
import buffet.app_web.service.GoogleService;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("api/eventos")
public class GoogleController {
    @Autowired
    private GoogleService googleService;

    @GetMapping
    public List<Google> listarTodos() throws GeneralSecurityException, IOException {
        return googleService.listarEventos();
    }

    @GetMapping("/alfabetica")
    public Google[] listarOrdemAlfabetica() throws GeneralSecurityException, IOException {
        return googleService.listarEventosAlfabetica();
    }

    @GetMapping("/pesquisa")
    public int pesquisaBinaria(@RequestParam String summary) throws GeneralSecurityException, IOException {
        return googleService.pesquisaBinaria(listarOrdemAlfabetica(), summary);
    }



}
