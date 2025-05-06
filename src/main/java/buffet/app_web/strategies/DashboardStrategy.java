package buffet.app_web.strategies;

import buffet.app_web.dto.response.dashboard.*;

import java.util.List;

public interface DashboardStrategy {
    List<TipoEventoContagemDto> countOrcamentosByTipoEvento(Long buffetId);
    ResumoFinanceiroDto getResumoFinanceiroMensal(Long buffetId);
    Double getPercentualEventosCancelados(Long buffetId);
    List<TopEventoDto> findTop3ByLucro(Long buffetId);
    List<TopEventoDto> findTop3ByDespesa(Long buffetId);
    Double getFaturamentoMedioPorEvento(Long buffetId);
    List<FaturamentoDespesaTipoEventoDto> getFaturamentoEDespesaPorTipoEvento(Long buffetId);
    List<PopularidadeDecoracaoDto> getPopularidadeDecoracao(Long buffetId);
    List<LucroPorTipoEventoDto> obterLucroPorTipoEvento(Long buffetId);
    List<QuantidadeOrcamentosPorMesDto> obterQuantidadeOrcamentosPorMes(Long buffetId);
    List<FaturamentoDespesaMesDto> getFaturamentoEDespesaPorMes(Long buffetId);

}
