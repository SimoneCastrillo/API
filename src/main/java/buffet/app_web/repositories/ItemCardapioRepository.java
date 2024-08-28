package buffet.app_web.repositories;

import buffet.app_web.entities.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {
}
