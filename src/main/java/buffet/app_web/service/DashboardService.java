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

    public List<TipoEventoContagemDto> countOrcamentosByTipoEvento(Long buffetId) {
        return orcamentoRepository.countOrcamentosByTipoEvento(buffetId);
    }

    public ResumoFinanceiroDto getResumoFinanceiroMensal(Long buffetId) {
        return orcamentoRepository.getResumoFinanceiroMensal(buffetId, LocalDate.now());
    }

    public Double getPercentualEventosCancelados(Long buffetId) {
        return orcamentoRepository.getPercentualEventosCancelados(buffetId);
    }

    public List<TopEventoDto> findTop3ByLucro(Long buffetId) {
        return orcamentoRepository.findTop3ByLucro(buffetId);

    }

    public List<TopEventoDto> findTop3ByDespesa(Long buffetId) {
        return orcamentoRepository.findTop3ByDespesa(buffetId);
    }

    public Double getFaturamentoMedioPorEvento(Long buffetId) {
        return orcamentoRepository.getFaturamentoMedioPorEvento(buffetId);
    }

    public List<FaturamentoDespesaTipoEventoDto> getFaturamentoEDespesaPorTipoEvento(Long buffetId) {
        return orcamentoRepository.getFaturamentoEDespesaPorTipoEvento(buffetId);
    }

    public List<PopularidadeDecoracaoDto> getPopularidadeDecoracao(Long buffetId) {
        return orcamentoRepository.getPopularidadeDecoracao(buffetId);
    }

    public List<LucroPorTipoEventoDto> obterLucroPorTipoEvento(Long buffetId) {
        return orcamentoRepository.calcularLucroPorTipoEvento(buffetId);
    }

    public List<QuantidadeOrcamentosPorMesDto> obterQuantidadeOrcamentosPorMes(Long buffetId) {
        return orcamentoRepository.calcularQuantidadeOrcamentosPorMes(buffetId);
    }

    public List<FaturamentoDespesaMesDto> getFaturamentoEDespesaPorMes(Long buffetId) {
        return orcamentoRepository.getFaturamentoEDespesaPorMes(buffetId);
    }
}
