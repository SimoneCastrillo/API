package buffet.app_web.strategies;

import buffet.app_web.dto.response.dashboard.TipoEventoContagemDto;
import buffet.app_web.entities.Orcamento;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrcamentoStrategy {
    List<Orcamento> listarTodos();
    Orcamento buscarPorId(Integer id);
    Orcamento salvar(Orcamento item, Integer tipoEventoId, Integer usuarioId, Integer decoracaoId, Long buffetId);
    Orcamento atualizar(Orcamento item, Integer tipoEventoId, Integer usuarioId, Integer decoracaoId, Long buffetId, Authentication authentication);
    void deletar(Integer id);
    Orcamento cancelarEvento(int id, Authentication authentication);
    Orcamento confirmarEvento(int id);
    Orcamento confirmarDadosDoEvento(Orcamento orcamento, Integer tipoEventoId, Integer decoracaoId);
    List<Orcamento> findByUsuarioId(int id);
    void finalizarOrcamentosExpirados();
    Orcamento desfazerCancelamento(int id);
}
