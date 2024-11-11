package buffet.app_web.strategies;

import buffet.app_web.entities.Orcamento;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface OrcamentoStrategy {
    List<Orcamento> listarTodos();
    Orcamento buscarPorId(Integer id);
    Orcamento salvar(Orcamento item, Integer tipoEventoId, Integer usuarioId, Integer decoracaoId);
    Orcamento atualizar(Orcamento item, Integer tipoEventoId, Integer usuarioId, Integer decoracaoId, Authentication authentication);
    void deletar(Integer id);
    Orcamento cancelarEvento(int id, Authentication authentication);
    Orcamento confirmarEvento(int id);
    Orcamento confirmarDadosDoEvento(Orcamento orcamento, Integer tipoEventoId, Integer decoracaoId);
    List<Orcamento> findByUsuarioId(int id);
    void finalizarOrcamentosExpirados();
}
