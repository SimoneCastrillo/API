package buffet.app_web.service;

import buffet.app_web.entities.Usuario;
import buffet.app_web.repositories.UsuarioRepository;
import buffet.app_web.strategies.UsuarioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UsuarioStrategy {
    @Autowired

    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos(){
        return  usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id){
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Usuario salvar(Usuario usuario){
        return  usuarioRepository.save(usuario);
    }

    public void deletar(Integer id){
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }


}
