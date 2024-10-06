package buffet.app_web.repositories;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecoracaoRepository extends JpaRepository<Decoracao, Integer> {
    List<Decoracao> findByTipoEventoNome(String nome);
}
