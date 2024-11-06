package buffet.app_web.service;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.Orcamento;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.entities.Usuario;
import buffet.app_web.repositories.OrcamentoRepository;
import buffet.app_web.strategies.OrcamentoStrategy;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService implements OrcamentoStrategy {
    @Autowired
    private OrcamentoRepository orcamentoRepository;
    @Autowired
    private TipoEventoService tipoEventoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DecoracaoService decoracaoService;
    @Autowired
    private GoogleService googleService;

    @Override
    public List<Orcamento> listarTodos() {
        return orcamentoRepository.findAll();
    }

    @Override
    public Orcamento buscarPorId(Integer id) {
        return orcamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Orcamento salvar(Orcamento orcamento, Integer tipoEventoId, Integer usuarioId, Integer decoracaoId) {
        TipoEvento tipoEvento = tipoEventoService.buscarPorId(tipoEventoId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Decoracao decoracao = decoracaoService.buscarPorId(decoracaoId);

        orcamento.setTipoEvento(tipoEvento);
        orcamento.setUsuario(usuario);
        orcamento.setDecoracao(decoracao);

        googleService.criarEvento(orcamento);
        return orcamentoRepository.save(orcamento);
    }

    public Orcamento atualizar(Orcamento orcamento, Integer tipoEventoId, Integer usuarioId, Integer decoracaoId) {
        TipoEvento tipoEvento = tipoEventoService.buscarPorId(tipoEventoId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Decoracao decoracao = decoracaoService.buscarPorId(decoracaoId);
//        Orcamento orcamento1 = buscarPorId(orcamento.getId());
//        String eventoId = orcamento1.getGoogleEventoId();

        orcamento.setTipoEvento(tipoEvento);
        orcamento.setUsuario(usuario);
        orcamento.setDecoracao(decoracao);
//        orcamento.setGoogleEventoId(eventoId);

        try {
            googleService.atualizarEvento("primary", orcamento);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return orcamentoRepository.save(orcamento);
    }

    @Override
    public void deletar(Integer id) {
        Orcamento orcamento = buscarPorId(id);
        try {
            googleService.deletarEvento("primary", orcamento);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
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

    public Integer countByUsuarioId(Integer usuarioId){
        return orcamentoRepository.countByUsuarioId(usuarioId);
    }
}
