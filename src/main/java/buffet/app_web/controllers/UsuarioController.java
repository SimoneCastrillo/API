package buffet.app_web.controllers;

import buffet.app_web.entities.Usuario;
import buffet.app_web.service.UsuarioService;
import buffet.app_web.strategies.UsuarioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioStrategy usuarioStrategy;

    @GetMapping
    public List<Usuario> listarTodos(){
        return usuarioStrategy.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable int id){
        Optional<Usuario> usuarioOpt = usuarioStrategy.buscarPorId(id);
        return usuarioOpt.orElse(null);
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario){
        return  usuarioStrategy.salvar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable int id, @RequestBody Usuario usuarioAtualizado){
        if(usuarioStrategy.buscarPorId(id).isPresent()){
            usuarioAtualizado.setId(id);
            return usuarioStrategy.salvar(usuarioAtualizado);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id){
        if(usuarioStrategy.buscarPorId(id).isPresent()){
            usuarioStrategy.deletar(id);
            return  "Usuário deletado com sucesso!";
        }
        return "Usuário não deletado";
    }

}
