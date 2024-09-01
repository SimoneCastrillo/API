package buffet.app_web.controllers;

import buffet.app_web.entities.Usuario;
import buffet.app_web.service.UsuarioService;
import buffet.app_web.strategies.UsuarioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioStrategy usuarioStrategy;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        if (usuarioStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarioStrategy.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id){
        Optional<Usuario> usuarioOpt = usuarioStrategy.buscarPorId(id);

        if (usuarioOpt.isPresent()){
            return ResponseEntity.status(200).body(usuarioOpt.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){
        usuario.setId(null);
        return ResponseEntity.status(201).body(usuarioStrategy.salvar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable int id, @RequestBody Usuario usuarioAtualizado){
        if(usuarioStrategy.buscarPorId(id).isPresent()){
            usuarioAtualizado.setId(id);
            usuarioStrategy.salvar(usuarioAtualizado);

            return ResponseEntity.status(200).body(usuarioAtualizado);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if(usuarioStrategy.buscarPorId(id).isPresent()){
            usuarioStrategy.deletar(id);

            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
