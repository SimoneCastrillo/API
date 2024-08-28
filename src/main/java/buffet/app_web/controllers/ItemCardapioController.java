package buffet.app_web.controllers;

import buffet.app_web.entities.ItemCardapio;
import buffet.app_web.service.ItemCardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens-cardapio")
public class ItemCardapioController {
    @Autowired
    private ItemCardapioService itemCardapioService;

    @GetMapping
    public List<ItemCardapio> listarTodos(){
        return itemCardapioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ItemCardapio buscarPorId(@PathVariable int id){
        Optional<ItemCardapio> itemOpt = itemCardapioService.buscarPorId(id);
        return itemOpt.orElse(null);
    }

    @PostMapping
    public ItemCardapio criar(@RequestBody ItemCardapio item){
        return itemCardapioService.salvar(item);
    }

    @PutMapping("/{id}")
    public ItemCardapio atualizar(@PathVariable int id, @RequestBody ItemCardapio itemAtualizado){
        if (itemCardapioService.buscarPorId(id).isPresent()){
            itemAtualizado.setId(id);
            return itemCardapioService.salvar(itemAtualizado);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id){
        if (itemCardapioService.buscarPorId(id).isPresent()){
            itemCardapioService.deletar(id);

            return "Item deletado com sucesso!";
        }

        return "Item não encontrado";
    }
}
