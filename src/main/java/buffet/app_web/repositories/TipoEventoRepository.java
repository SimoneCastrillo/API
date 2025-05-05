package buffet.app_web.repositories;

import buffet.app_web.entities.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {
    List<TipoEvento> findAllByBuffetId(Long buffetId);
}
