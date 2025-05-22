package buffet.app_web.service;

import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.repositories.TipoEventoRepository;
import buffet.app_web.strategies.TipoEventoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEventoService implements TipoEventoStrategy {
    @Autowired
    private TipoEventoRepository tipoEventoRepository;
    @Autowired
    private BuffetService buffetService;

    @Override
    public List<TipoEvento> listarTodos(Long buffetId){
        return tipoEventoRepository.findAllByBuffetId(buffetId);
    }
    @Override
    public TipoEvento buscarPorId(Integer id){
        return tipoEventoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Override
    public TipoEvento salvar(TipoEvento tipoEvento, Long buffetId){
        Buffet buffet = buffetService.buscarPorId(buffetId);

        tipoEvento.setBuffet(buffet);
        return tipoEventoRepository.save(tipoEvento);
    }
    @Override
    public void deletar(Integer id){
        tipoEventoRepository.deleteById(id);
    }

}
