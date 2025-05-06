package buffet.app_web.repositories;

import buffet.app_web.entities.Buffet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuffetRepository extends JpaRepository<Buffet, Long> {
}
