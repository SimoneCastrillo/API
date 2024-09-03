package buffet.app_web.repositories;

import buffet.app_web.entities.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {
}
