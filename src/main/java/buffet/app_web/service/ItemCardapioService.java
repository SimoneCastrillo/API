package buffet.app_web.service;

import buffet.app_web.entities.ItemCardapio;
import buffet.app_web.repositories.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCardapioService {
    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    public List<ItemCardapio> listarTodos(){
        return itemCardapioRepository.findAll();
    }

    public Optional<ItemCardapio> buscarPorId(Integer id){
        return itemCardapioRepository.findById(id);
    }

    public ItemCardapio salvar(ItemCardapio item){
        return itemCardapioRepository.save(item);
    }

    public void deletar(Integer id){
        itemCardapioRepository.deleteById(id);
    }
}
