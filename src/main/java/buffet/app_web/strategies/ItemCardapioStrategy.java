package buffet.app_web.strategies;

import buffet.app_web.entities.ItemCardapio;

import java.util.List;
import java.util.Optional;

public interface ItemCardapioStrategy {
    List<ItemCardapio> listarTodos();
    Optional<ItemCardapio> buscarPorId(Integer id);
    ItemCardapio salvar(ItemCardapio item);
    void deletar(Integer id);
}
