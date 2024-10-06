package buffet.app_web.service;

import buffet.app_web.entities.Orcamento;
import buffet.app_web.repositories.OrcamentoRepository;
import buffet.app_web.strategies.OrcamentoStrategy;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Orcamento buscarPorId(Integer id) {
        return orcamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Orcamento salvar(Orcamento orcamento) {
        return orcamentoRepository.save(orcamento);
    }

    @Override
    public void deletar(Integer id) {
        Orcamento orcamento = buscarPorId(id);
        orcamentoRepository.deleteById(orcamento.getId());
    }

    @Override
    public Orcamento cancelarEvento(int id) {
        Orcamento orcamento = buscarPorId(id);

        if (orcamento.getCancelado() || orcamento.getStatus().equals("FINALIZADO")){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        orcamento.setId(id);
        orcamento.setCancelado(true);
        orcamento.setStatus(orcamento.getStatus());
        return orcamentoRepository.save(orcamento);
    }
}
