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

    @Query("SELECT new buffet.app_web.dto.response.dashboard.TipoEventoContagemDto(t.nome, COUNT(o)) " +
            "FROM Orcamento o JOIN o.tipoEvento t " +
            "WHERE o.buffet.id = :buffetId " +
            "GROUP BY t.nome")
    List<TipoEventoContagemDto> countOrcamentosByTipoEvento(@Param("buffetId") Long buffetId);

    @Query("SELECT (COUNT(o) * 1.0 / (SELECT COUNT(o2) FROM Orcamento o2)) * 100 " +
            "FROM Orcamento o " +
            "WHERE o.cancelado = true AND o.buffet.id = :buffetId")
    Double getPercentualEventosCancelados(@Param("buffetId") Long buffetId);

    @Query("SELECT new buffet.app_web.dto.response.dashboard.ResumoFinanceiroDto(" +
            "SUM(o.lucro), SUM(o.faturamento), SUM(o.despesa)) " +
            "FROM Orcamento o " +
            "WHERE MONTH(o.dataEvento) = MONTH(:dataAtual) AND YEAR(o.dataEvento) = YEAR(:dataAtual) " +
            "AND o.buffet.id = :buffetId " +
            "AND o.status IN ('CONFIRMADO', 'FINALIZADO')")
    ResumoFinanceiroDto getResumoFinanceiroMensal(@Param("buffetId") Long buffetId, @Param("dataAtual") LocalDate dataAtual);

    @Query("SELECT new buffet.app_web.dto.response.dashboard.TopEventoDto(o.tipoEvento.nome, SUM(o.lucro)) " +
            "FROM Orcamento o " +
            "WHERE o.status IN ('CONFIRMADO', 'FINALIZADO') AND o.buffet.id = :buffetId " +
            "GROUP BY o.tipoEvento.nome " +
            "ORDER BY SUM(o.lucro) DESC")
    List<TopEventoDto> findTop3ByLucro(@Param("buffetId") Long buffetId); // aplique PageRequest no serviço para limitar a 3

    @Query("SELECT new buffet.app_web.dto.response.dashboard.TopEventoDto(o.tipoEvento.nome, SUM(o.despesa)) " +
            "FROM Orcamento o " +
            "WHERE o.status IN ('CONFIRMADO', 'FINALIZADO') AND o.buffet.id = :buffetId " +
            "GROUP BY o.tipoEvento.nome " +
            "ORDER BY SUM(o.despesa) DESC")
    List<TopEventoDto> findTop3ByDespesa(@Param("buffetId") Long buffetId); // idem acima

    @Query("SELECT AVG(o.faturamento) FROM Orcamento o " +
            "WHERE o.status IN ('CONFIRMADO', 'FINALIZADO') AND o.buffet.id = :buffetId")
    Double getFaturamentoMedioPorEvento(@Param("buffetId") Long buffetId);

    @Query("SELECT new buffet.app_web.dto.response.dashboard.FaturamentoDespesaTipoEventoDto(" +
            "o.tipoEvento.nome, SUM(o.faturamento), SUM(o.despesa)) " +
            "FROM Orcamento o " +
            "WHERE o.status IN ('CONFIRMADO', 'FINALIZADO') AND o.buffet.id = :buffetId " +
            "GROUP BY o.tipoEvento.nome")
    List<FaturamentoDespesaTipoEventoDto> getFaturamentoEDespesaPorTipoEvento(@Param("buffetId") Long buffetId);

    @Query("SELECT new buffet.app_web.dto.response.dashboard.PopularidadeDecoracaoDto(" +
            "o.decoracao.nome, COUNT(o.decoracao), AVG(o.lucro), COUNT(o)) " +
            "FROM Orcamento o " +
            "WHERE o.status IN ('CONFIRMADO', 'FINALIZADO') AND o.buffet.id = :buffetId " +
            "GROUP BY o.decoracao.nome")
    List<PopularidadeDecoracaoDto> getPopularidadeDecoracao(@Param("buffetId") Long buffetId);

    @Query("SELECT new buffet.app_web.dto.response.dashboard.LucroPorTipoEventoDto(t.nome, SUM(o.faturamento - o.despesa)) " +
            "FROM Orcamento o JOIN o.tipoEvento t " +
            "WHERE o.status IN ('CONFIRMADO', 'FINALIZADO') AND o.buffet.id = :buffetId " +
            "GROUP BY t.nome")
    List<LucroPorTipoEventoDto> calcularLucroPorTipoEvento(@Param("buffetId") Long buffetId);

    @Query("SELECT new buffet.app_web.dto.response.dashboard.QuantidadeOrcamentosPorMesDto(" +
            "MONTH(o.dataEvento), COUNT(o)) " +
            "FROM Orcamento o " +
            "WHERE o.buffet.id = :buffetId " +
            "GROUP BY MONTH(o.dataEvento) " +
            "ORDER BY MONTH(o.dataEvento)")
    List<QuantidadeOrcamentosPorMesDto> calcularQuantidadeOrcamentosPorMes(@Param("buffetId") Long buffetId);

    @Query("SELECT new buffet.app_web.dto.response.dashboard.FaturamentoDespesaMesDto(" +
            "MONTH(o.dataEvento), SUM(o.faturamento), SUM(o.despesa)) " +
            "FROM Orcamento o " +
            "WHERE o.status IN ('CONFIRMADO', 'FINALIZADO') AND o.buffet.id = :buffetId " +
            "GROUP BY MONTH(o.dataEvento) " +
            "ORDER BY MONTH(o.dataEvento)")
    List<FaturamentoDespesaMesDto> getFaturamentoEDespesaPorMes(@Param("buffetId") Long buffetId);
}
