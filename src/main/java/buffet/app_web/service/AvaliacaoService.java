package buffet.app_web.service;

import buffet.app_web.entities.Avaliacao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.repositories.AvaliacaoRepository;
import buffet.app_web.strategies.AvaliacaoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AvaliacaoService implements AvaliacaoStrategy {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private TipoEventoService tipoEventoService;

    @Override
    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    @Override
    public Avaliacao buscarPorId(int id) {
        return avaliacaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Avaliacao salvar(Avaliacao avaliacao, Integer tipoEventoId) {
        TipoEvento tipoEvento = tipoEventoService.buscarPorId(tipoEventoId);
        avaliacao.setTipoEvento(tipoEvento);

        return avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao atualizar(Avaliacao avaliacao) {
        buscarPorId(avaliacao.getId());
        avaliacao.setId(avaliacao.getId());

        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public void deletar(int id) {
        Avaliacao avaliacao = buscarPorId(id);
        avaliacaoRepository.deleteById(avaliacao.getId());
    }

    @Override
    public List<Avaliacao> listarUltimos5() {
        return avaliacaoRepository.findTop5ByOrderByIdDesc();
    }

    @Override
    public List<Avaliacao> listarPorTipoDeEvento(String nome) {
        return avaliacaoRepository.findByTipoEventoNome(nome);
    }
}
