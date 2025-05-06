package buffet.app_web.service;

import buffet.app_web.entities.Buffet;
import buffet.app_web.repositories.BuffetRepository;
import buffet.app_web.strategies.BuffetStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BuffetService implements BuffetStrategy {
    @Autowired
    private BuffetRepository buffetRepository;

    public List<Buffet> listar() {
        return buffetRepository.findAll();
    }

    public Buffet buscarPorId(Long id) {
        return buffetRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Buffet salvar(Buffet buffet) {
        return buffetRepository.save(buffet);
    }

    public Buffet atualizar(Buffet buffet, Long id) {
        buscarPorId(id);
        buffet.setId(id);

        return buffetRepository.save(buffet);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        buffetRepository.deleteById(id);
    }
}
