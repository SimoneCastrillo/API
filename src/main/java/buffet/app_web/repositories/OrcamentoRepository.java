package buffet.app_web.repositories;

import buffet.app_web.dto.response.dashboard.*;
import buffet.app_web.entities.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {
    @Query("SELECT COUNT(o) FROM Orcamento o WHERE o.usuario.id = :usuarioId")
    Integer countByUsuarioId(@Param("usuarioId") Integer usuarioId);
    List<Orcamento> findByUsuarioId(int id);
    List<Orcamento> findByStatusAndDataEventoBefore(String status, LocalDate dataEvento);

    @Query("SELECT new buffet.app_web.dto.response. dashboard.TipoEventoContagemDto(t.nome, COUNT(o)) " +
            "FROM Orcamento o JOIN o.tipoEvento t " +
            "GROUP BY t.nome")
    List<TipoEventoContagemDto> countOrcamentosByTipoEvento();

    @Query("SELECT new buffet.app_web.dto.response.dashboard.ResumoFinanceiroDto(" +
            "SUM(o.lucro), SUM(o.faturamento), SUM(o.despesa)) " +
            "FROM Orcamento o " +
            "WHERE MONTH(o.dataEvento) = MONTH(:dataAtual) AND YEAR(o.dataEvento) = YEAR(:dataAtual)")
    ResumoFinanceiroDto getResumoFinanceiroMensal(@Param("dataAtual") LocalDate dataAtual);

    @Query("SELECT (COUNT(o) * 1.0 / (SELECT COUNT(o2) FROM Orcamento o2)) * 100 " +
            "FROM Orcamento o " +
            "WHERE o.cancelado = true")
    Double getPercentualEventosCancelados();

    @Query("SELECT new buffet.app_web.dto.response.dashboard.TopEventoDto(o.tipoEvento.nome, SUM(o.lucro)) " +
            "FROM Orcamento o " +
            "GROUP BY o.tipoEvento.nome " +
            "ORDER BY SUM(o.lucro) DESC")
    List<TopEventoDto> findTop3ByLucro();

    @Query("SELECT new buffet.app_web.dto.response.dashboard.TopEventoDto(o.tipoEvento.nome, SUM(o.despesa)) " +
            "FROM Orcamento o " +
            "GROUP BY o.tipoEvento.nome " +
            "ORDER BY SUM(o.despesa) DESC")
    List<TopEventoDto> findTop3ByDespesa();

    @Query("SELECT AVG(o.faturamento) FROM Orcamento o")
    Double getFaturamentoMedioPorEvento();

    @Query("SELECT new buffet.app_web.dto.response.dashboard.FaturamentoDespesaTipoEventoDto(" +
            "o.tipoEvento.nome, SUM(o.faturamento), SUM(o.despesa)) " +
            "FROM Orcamento o " +
            "GROUP BY o.tipoEvento.nome")
    List<FaturamentoDespesaTipoEventoDto> getFaturamentoEDespesaPorTipoEvento();

    @Query("SELECT new buffet.app_web.dto.response.dashboard.PopularidadeDecoracaoDto(" +
            "o.decoracao.nome, COUNT(o.decoracao), AVG(o.lucro), COUNT(o)) " +
            "FROM Orcamento o " +
            "GROUP BY o.decoracao.nome")
    List<PopularidadeDecoracaoDto> getPopularidadeDecoracao();

}
