package buffet.app_web.service;

import buffet.app_web.entities.Usuario;
import buffet.app_web.repositories.UsuarioRepository;
import buffet.app_web.strategies.UsuarioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UsuarioStrategy {
    @Autowired

    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos(){
        return  usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id){
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario){
        return  usuarioRepository.save(usuario);
    }

    public void deletar(Integer id){
        usuarioRepository.deleteById(id);
    }


}
