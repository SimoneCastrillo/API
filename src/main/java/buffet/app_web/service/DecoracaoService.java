package buffet.app_web.service;

import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.repositories.DecoracaoRepository;
import buffet.app_web.strategies.DecoracaoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DecoracaoService implements DecoracaoStrategy {
    @Autowired
    private DecoracaoRepository decoracaoRepository;
    @Autowired
    private TipoEventoService tipoEventoService;
    @Autowired
    private BuffetService buffetService;
    @Override
    public List<Decoracao> listarTodos(Long buffetId) {
        return decoracaoRepository.findByBuffetId(buffetId);
    }

    @Override
    public Decoracao buscarPorId(int id) {
        return decoracaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Decoracao salvar(Decoracao decoracao, Integer tipoEventoId, Long buffetId) {
        Buffet buffet = buffetService.buscarPorId(buffetId);
        TipoEvento tipoEvento = tipoEventoService.buscarPorId(tipoEventoId);

        decoracao.setTipoEvento(tipoEvento);
        decoracao.setBuffet(buffet);

        return decoracaoRepository.save(decoracao);
    }

    @Override
    public void deletar(int id) {
        Decoracao decoracao = buscarPorId(id);
        decoracaoRepository.deleteById(decoracao.getId());
    }

    @Override
    public List<Decoracao> listarPorTipoDeEvento(Integer tipoEventoId) {
        return decoracaoRepository.findByTipoEventoId(tipoEventoId);
    }
}
