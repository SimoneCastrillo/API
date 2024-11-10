package buffet.app_web.repositories;

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
}
