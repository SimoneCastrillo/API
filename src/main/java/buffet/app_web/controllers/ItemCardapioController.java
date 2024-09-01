package buffet.app_web.controllers;

import buffet.app_web.entities.ItemCardapio;
import buffet.app_web.service.ItemCardapioService;
import buffet.app_web.strategies.ItemCardapioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens-cardapio")
public class ItemCardapioController {
    @Autowired
    private ItemCardapioStrategy itemCardapioStrategy;

    @GetMapping
    public ResponseEntity<List<ItemCardapio>> listarTodos(){
        if (itemCardapioStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(itemCardapioStrategy.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapio> buscarPorId(@PathVariable int id){
        Optional<ItemCardapio> itemOpt = itemCardapioStrategy.buscarPorId(id);
        if (itemOpt.isPresent()){
            return ResponseEntity.status(200).body(itemOpt.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<ItemCardapio> criar(@RequestBody ItemCardapio item){
        item.setId(null);
        return ResponseEntity.status(201).body(itemCardapioStrategy.salvar(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCardapio> atualizar(@PathVariable int id, @RequestBody ItemCardapio itemAtualizado){
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
