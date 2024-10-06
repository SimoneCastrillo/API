package buffet.app_web.strategies;

import buffet.app_web.entities.Decoracao;

import java.util.List;
import java.util.Optional;

public interface DecoracaoStrategy {
    List<Decoracao> listarTodos();
    Decoracao buscarPorId(int id);
    Decoracao salvar(Decoracao decoracao);
    void deletar(int id);

}
