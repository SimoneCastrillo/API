package buffet.app_web.service;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.repositories.DecoracaoRepository;
import buffet.app_web.strategies.DecoracaoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DecoracaoService implements DecoracaoStrategy {
    @Autowired
    private DecoracaoRepository decoracaoRepository;
    @Override
    public List<Decoracao> listarTodos() {
        return decoracaoRepository.findAll();
    }

    @Override
    public Optional<Decoracao> buscarPorId(int id) {
        return decoracaoRepository.findById(id);
    }

    @Override
    public Decoracao salvar(Decoracao decoracao) {
        return decoracaoRepository.save(decoracao);
    }

    @Override
    public void deletar(int id) {
        decoracaoRepository.deleteById(id);
    }
}
