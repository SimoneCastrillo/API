package buffet.app_web.service;

import buffet.app_web.dto.response.dashboard.*;
import buffet.app_web.repositories.OrcamentoRepository;
import buffet.app_web.strategies.DashboardStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService implements DashboardStrategy {
    @Autowired
    OrcamentoRepository orcamentoRepository;

    public List<TipoEventoContagemDto> countOrcamentosByTipoEvento() {
        return orcamentoRepository.countOrcamentosByTipoEvento();
    }

    public ResumoFinanceiroDto getResumoFinanceiroMensal() {
        return orcamentoRepository.getResumoFinanceiroMensal(LocalDate.now());
    }

    public Double getPercentualEventosCancelados() {
        return orcamentoRepository.getPercentualEventosCancelados();
    }

    public List<TopEventoDto> findTop3ByLucro() {
        return orcamentoRepository.findTop3ByLucro();

    }

    public List<TopEventoDto> findTop3ByDespesa() {
        return orcamentoRepository.findTop3ByDespesa();
    }

    public Double getFaturamentoMedioPorEvento() {
        return orcamentoRepository.getFaturamentoMedioPorEvento();
    }

    public List<FaturamentoDespesaTipoEventoDto> getFaturamentoEDespesaPorTipoEvento() {
        return orcamentoRepository.getFaturamentoEDespesaPorTipoEvento();
    }

    public List<PopularidadeDecoracaoDto> getPopularidadeDecoracao() {
        return orcamentoRepository.getPopularidadeDecoracao();
    }
}
