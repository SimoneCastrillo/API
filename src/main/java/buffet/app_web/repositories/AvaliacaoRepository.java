package buffet.app_web.repositories;

import buffet.app_web.entities.Avaliacao;
import buffet.app_web.entities.Decoracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
}
