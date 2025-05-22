package buffet.app_web.strategies;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;

import java.util.List;
import java.util.Optional;

public interface DecoracaoStrategy {
    List<Decoracao> listarTodos(Long buffetId);
    Decoracao buscarPorId(int id);
    Decoracao salvar(Decoracao decoracao, Integer tipoEventoId, Long buffetId);
    void deletar(int id);
    List<Decoracao> listarPorTipoDeEvento(Integer tipoEventoId);

}
