package buffet.app_web.service;

import buffet.app_web.entities.TipoEvento;
import buffet.app_web.repositories.TipoEventoRepository;
import buffet.app_web.strategies.TipoEventoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEventoService implements TipoEventoStrategy {
    @Autowired
    private TipoEventoRepository itemCardapioRepository;

    public List<TipoEvento> listarTodos(){
        return itemCardapioRepository.findAll();
    }

    public Optional<TipoEvento> buscarPorId(Integer id){
        return itemCardapioRepository.findById(id);
    }

    public TipoEvento salvar(TipoEvento item){
        return itemCardapioRepository.save(item);
    }

    public void deletar(Integer id){
        itemCardapioRepository.deleteById(id);
    }

}
