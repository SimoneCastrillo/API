package buffet.app_web.controllers;

import buffet.app_web.dto.response.dashboard.*;
import buffet.app_web.strategies.DashboardStrategy;
import buffet.app_web.strategies.OrcamentoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController {

    @Autowired
    private DashboardStrategy dashboardStrategy;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tipo-evento-contagem")
    public ResponseEntity<List<TipoEventoContagemDto>> getOrcamentoCountByTipoEvento() {
        List<TipoEventoContagemDto> resultado = dashboardStrategy.countOrcamentosByTipoEvento();
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/resumo-financeiro")
    public ResponseEntity<ResumoFinanceiroDto> getResumoFinanceiroMensal() {
        ResumoFinanceiroDto resumo = dashboardStrategy.getResumoFinanceiroMensal();
        if (resumo == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resumo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/percentual-cancelados")
    public ResponseEntity<Double> getPercentualEventosCancelados() {
        Double percentual = dashboardStrategy.getPercentualEventosCancelados();
        if (percentual == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(percentual);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top3-lucro")
    public ResponseEntity<List<TopEventoDto>> getTop3TiposEventoPorLucro() {
        List<TopEventoDto> topEventos = dashboardStrategy.findTop3ByLucro();
        if (topEventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topEventos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top3-despesa")
    public ResponseEntity<List<TopEventoDto>> getTop3TiposEventoPorDespesa() {
        List<TopEventoDto> topEventos = dashboardStrategy.findTop3ByDespesa();
        if (topEventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topEventos);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/faturamento-medio")
    public ResponseEntity<Double> getFaturamentoMedioPorEvento() {
        Double faturamentoMedio = dashboardStrategy.getFaturamentoMedioPorEvento();
        if (faturamentoMedio == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(faturamentoMedio);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/faturamento-despesa")
    public ResponseEntity<List<FaturamentoDespesaTipoEventoDto>> getFaturamentoEDespesaPorTipoEvento() {
        List<FaturamentoDespesaTipoEventoDto> resultado = dashboardStrategy.getFaturamentoEDespesaPorTipoEvento();
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/popularidade-decoracao")
    public ResponseEntity<List<PopularidadeDecoracaoDto>> getPopularidadeDecoracao() {
        List<PopularidadeDecoracaoDto> resultado = dashboardStrategy.getPopularidadeDecoracao();
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/lucro-por-tipo-evento")
    public ResponseEntity<List<LucroPorTipoEventoDto>> getLucroPorTipoEvento() {
        List<LucroPorTipoEventoDto> lucros = dashboardStrategy.obterLucroPorTipoEvento();
        return ResponseEntity.ok(lucros);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/quantidade-por-mes")
    public ResponseEntity<List<QuantidadeOrcamentosPorMesDto>> getQuantidadeOrcamentosPorMes() {
        List<QuantidadeOrcamentosPorMesDto> resultado = dashboardStrategy.obterQuantidadeOrcamentosPorMes();
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/faturamento-despesa-por-mes")
    public ResponseEntity<List<FaturamentoDespesaMesDto>> getFaturamentoEDespesaPorMes() {
        List<FaturamentoDespesaMesDto> resultado = dashboardStrategy.getFaturamentoEDespesaPorMes();
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

}
