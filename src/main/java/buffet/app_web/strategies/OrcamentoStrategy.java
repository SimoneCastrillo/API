package buffet.app_web.strategies;

import buffet.app_web.entities.Orcamento;

import java.util.List;
import java.util.Optional;

public interface OrcamentoStrategy {
    List<Orcamento> listarTodos();
    Optional<Orcamento> buscarPorId(Integer id);
    Orcamento salvar(Orcamento item);
    void deletar(Integer id);
}
