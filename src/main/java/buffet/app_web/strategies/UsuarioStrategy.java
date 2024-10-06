package buffet.app_web.strategies;

import buffet.app_web.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioStrategy {
    List<Usuario> listarTodos();
    Usuario buscarPorId(Integer id);
    Usuario salvar(Usuario usuario);
    void deletar(Integer id);
}


