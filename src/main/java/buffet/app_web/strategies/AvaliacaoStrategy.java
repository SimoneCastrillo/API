package buffet.app_web.strategies;

import buffet.app_web.entities.Avaliacao;

import java.util.List;

public interface AvaliacaoStrategy {
    List<Avaliacao> listarTodos();
    Avaliacao buscarPorId(int id);
    Avaliacao salvar(Avaliacao avaliacao);
    void deletar(int id);
    List<Avaliacao> listarUltimos5();
    List<Avaliacao> listarPorTipoDeEvento(String nome);

}
