package buffet.app_web.service;

import buffet.app_web.entities.Orcamento;
import buffet.app_web.repositories.OrcamentoRepository;
import buffet.app_web.strategies.OrcamentoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService implements OrcamentoStrategy {
    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Override
    public List<Orcamento> listarTodos() {
        return orcamentoRepository.findAll();
    }

    @Override
    public Optional<Orcamento> buscarPorId(Integer id) {
        return orcamentoRepository.findById(id);
    }

    @Override
    public Orcamento salvar(Orcamento orcamento) {
        return orcamentoRepository.save(orcamento);
    }

    @Override
    public void deletar(Integer id) {
        orcamentoRepository.deleteById(id);
    }
}
