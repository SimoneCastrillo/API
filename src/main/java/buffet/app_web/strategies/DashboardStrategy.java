package buffet.app_web.strategies;

import buffet.app_web.dto.response.dashboard.*;

import java.util.List;

public interface DashboardStrategy {
    List<TipoEventoContagemDto> countOrcamentosByTipoEvento();
    ResumoFinanceiroDto getResumoFinanceiroMensal();
    Double getPercentualEventosCancelados();
    List<TopEventoDto> findTop3ByLucro();
    List<TopEventoDto> findTop3ByDespesa();
    Double getFaturamentoMedioPorEvento();
    List<FaturamentoDespesaTipoEventoDto> getFaturamentoEDespesaPorTipoEvento();
    List<PopularidadeDecoracaoDto> getPopularidadeDecoracao();

}
