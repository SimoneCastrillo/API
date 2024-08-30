package buffet.app_web.controllers;

import buffet.app_web.entities.ItemCardapio;
import buffet.app_web.service.ItemCardapioService;
import buffet.app_web.strategies.ItemCardapioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens-cardapio")
public class ItemCardapioController {
    @Autowired
    private ItemCardapioStrategy itemCardapioStrategy;

    @GetMapping
    public List<ItemCardapio> listarTodos(){
        return itemCardapioStrategy.listarTodos();
    }

    @GetMapping("/{id}")
    public ItemCardapio buscarPorId(@PathVariable int id){
        Optional<ItemCardapio> itemOpt = itemCardapioStrategy.buscarPorId(id);
        return itemOpt.orElse(null);
    }

    @PostMapping
    public ItemCardapio criar(@RequestBody ItemCardapio item){
        return itemCardapioStrategy.salvar(item);
    }

    @PutMapping("/{id}")
    public ItemCardapio atualizar(@PathVariable int id, @RequestBody ItemCardapio itemAtualizado){
        if (itemCardapioStrategy.buscarPorId(id).isPresent()){
            itemAtualizado.setId(id);
            return itemCardapioStrategy.salvar(itemAtualizado);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id){
        if (itemCardapioStrategy.buscarPorId(id).isPresent()){
            itemCardapioStrategy.deletar(id);

            return "Item deletado com sucesso!";
        }

        return "Item não encontrado";
    }
}
