package buffet.app_web.service;

import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.Endereco;
import buffet.app_web.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BuffetService buffetService;

    public List<Endereco> listar() {
        return enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Endereco salvar(Endereco endereco, Long buffetId) {
        Buffet buffet = buffetService.buscarPorId(buffetId);
        endereco.setBuffet(buffet);

        return enderecoRepository.save(endereco);
    }

    public Endereco atualizar(Endereco endereco, Long id) {
        buscarPorId(id);
        endereco.setId(id);
        return enderecoRepository.save(endereco);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        enderecoRepository.deleteById(id);
    }
}

