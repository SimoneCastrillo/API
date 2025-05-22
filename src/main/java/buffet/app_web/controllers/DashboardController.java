package buffet.app_web.controllers;

import buffet.app_web.dto.response.dashboard.*;
import buffet.app_web.strategies.DashboardStrategy;
import buffet.app_web.strategies.OrcamentoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController {

    @Autowired
    private DashboardStrategy dashboardStrategy;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tipo-evento-contagem/{buffetId}")
    public ResponseEntity<List<TipoEventoContagemDto>> getOrcamentoCountByTipoEvento(@PathVariable Long buffetId) {
        List<TipoEventoContagemDto> resultado = dashboardStrategy.countOrcamentosByTipoEvento(buffetId);
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/resumo-financeiro/{buffetId}")
    public ResponseEntity<ResumoFinanceiroDto> getResumoFinanceiroMensal(@PathVariable Long buffetId) {
        ResumoFinanceiroDto resumo = dashboardStrategy.getResumoFinanceiroMensal(buffetId);
        if (resumo == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resumo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/percentual-cancelados/{buffetId}")
    public ResponseEntity<Double> getPercentualEventosCancelados(@PathVariable Long buffetId) {
        Double percentual = dashboardStrategy.getPercentualEventosCancelados(buffetId);
        if (percentual == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(percentual);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top3-lucro/{buffetId}")
    public ResponseEntity<List<TopEventoDto>> getTop3TiposEventoPorLucro(@PathVariable Long buffetId) {
        List<TopEventoDto> topEventos = dashboardStrategy.findTop3ByLucro(buffetId);
        if (topEventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topEventos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top3-despesa/{buffetId}")
    public ResponseEntity<List<TopEventoDto>> getTop3TiposEventoPorDespesa(@PathVariable Long buffetId) {
        List<TopEventoDto> topEventos = dashboardStrategy.findTop3ByDespesa(buffetId);
        if (topEventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topEventos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/faturamento-medio/{buffetId}")
    public ResponseEntity<Double> getFaturamentoMedioPorEvento(@PathVariable Long buffetId) {
        Double faturamentoMedio = dashboardStrategy.getFaturamentoMedioPorEvento(buffetId);
        if (faturamentoMedio == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(faturamentoMedio);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/faturamento-despesa/{buffetId}")
    public ResponseEntity<List<FaturamentoDespesaTipoEventoDto>> getFaturamentoEDespesaPorTipoEvento(@PathVariable Long buffetId) {
        List<FaturamentoDespesaTipoEventoDto> resultado = dashboardStrategy.getFaturamentoEDespesaPorTipoEvento(buffetId);
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/popularidade-decoracao/{buffetId}")
    public ResponseEntity<List<PopularidadeDecoracaoDto>> getPopularidadeDecoracao(@PathVariable Long buffetId) {
        List<PopularidadeDecoracaoDto> resultado = dashboardStrategy.getPopularidadeDecoracao(buffetId);
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/lucro-por-tipo-evento/{buffetId}")
    public ResponseEntity<List<LucroPorTipoEventoDto>> getLucroPorTipoEvento(@PathVariable Long buffetId) {
        List<LucroPorTipoEventoDto> lucros = dashboardStrategy.obterLucroPorTipoEvento(buffetId);
        return ResponseEntity.ok(lucros);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/quantidade-por-mes/{buffetId}")
    public ResponseEntity<List<QuantidadeOrcamentosPorMesDto>> getQuantidadeOrcamentosPorMes(@PathVariable Long buffetId) {
        List<QuantidadeOrcamentosPorMesDto> resultado = dashboardStrategy.obterQuantidadeOrcamentosPorMes(buffetId);
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/faturamento-despesa-por-mes/{buffetId}")
    public ResponseEntity<List<FaturamentoDespesaMesDto>> getFaturamentoEDespesaPorMes(@PathVariable Long buffetId) {
        List<FaturamentoDespesaMesDto> resultado = dashboardStrategy.getFaturamentoEDespesaPorMes(buffetId);
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

}
