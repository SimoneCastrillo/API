package buffet.app_web.controllers;

import buffet.app_web.entities.Usuario;
import buffet.app_web.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarTodos(){
    return usuarioService.listarTodos();
}

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable int id){
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        return usuarioOpt.orElse(null);
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario){
        return  usuarioService.salvar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable int id, @RequestBody Usuario usuarioAtualizado){
        if(usuarioService.buscarPorId(id).isPresent()){
            usuarioAtualizado.setId(id);
            return usuarioService.salvar(usuarioAtualizado);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id){
        if(usuarioService.buscarPorId(id).isPresent()){
            usuarioService.deletar(id);
            return  "Usuário deletado com sucesso!";
        }
        return "Usuário não deletado";
    }

}
