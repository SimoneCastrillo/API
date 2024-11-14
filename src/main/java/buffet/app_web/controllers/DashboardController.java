package buffet.app_web.controllers;

import buffet.app_web.dto.response.tipoevento.TipoEventoContagemDto;
import buffet.app_web.strategies.OrcamentoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController {

    @Autowired
    private OrcamentoStrategy orcamentoStrategy;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tipo-evento-count")
    public List<TipoEventoContagemDto> getOrcamentoCountByTipoEvento() {
        return orcamentoStrategy.countOrcamentosByTipoEvento();
    }
}
