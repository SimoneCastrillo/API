package buffet.app_web.strategies;

import buffet.app_web.entities.TipoEvento;

import java.util.List;
import java.util.Optional;

public interface TipoEventoStrategy {
    List<TipoEvento> listarTodos(Long buffetId);
    TipoEvento buscarPorId(Integer id);
    TipoEvento salvar(TipoEvento tipoEvento, Long buffetId);
    void deletar(Integer id);
}
