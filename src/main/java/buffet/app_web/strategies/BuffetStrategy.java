package buffet.app_web.strategies;

import buffet.app_web.entities.Buffet;

public interface BuffetStrategy {
    Buffet buscarPorId(Long id);
    Buffet salvar(Buffet buffet);
    Buffet atualizar(Buffet buffet, Long id);
    void deletar(Long id);
}
