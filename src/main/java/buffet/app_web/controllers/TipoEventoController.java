package buffet.app_web.controllers;

import buffet.app_web.entities.TipoEvento;
import buffet.app_web.strategies.TipoEventoStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-evento")
public class TipoEventoController {
    @Autowired
    private TipoEventoStrategy itemCardapioStrategy;

    @GetMapping
    public ResponseEntity<List<TipoEvento>> listarTodos(){
        if (itemCardapioStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(itemCardapioStrategy.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEvento> buscarPorId(@PathVariable int id){
        Optional<TipoEvento> itemOpt = itemCardapioStrategy.buscarPorId(id);
        if (itemOpt.isPresent()){
            return ResponseEntity.status(200).body(itemOpt.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<TipoEvento> criar(@RequestBody @Valid TipoEvento item){
        item.setId(null);
        return ResponseEntity.status(201).body(itemCardapioStrategy.salvar(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEvento> atualizar(@PathVariable int id, @RequestBody @Valid TipoEvento itemAtualizado){
        if (itemCardapioStrategy.buscarPorId(id).isPresent()){
            itemAtualizado.setId(id);
            itemCardapioStrategy.salvar(itemAtualizado);

            return ResponseEntity.status(200).body(itemAtualizado);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if (itemCardapioStrategy.buscarPorId(id).isPresent()){
            itemCardapioStrategy.deletar(id);

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }
}
