package buffet.app_web.service;

import buffet.app_web.dto.response.dashboard.TipoEventoContagemDto;
import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.Orcamento;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.entities.Usuario;
import buffet.app_web.repositories.OrcamentoRepository;
import buffet.app_web.strategies.OrcamentoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
        Decoracao decoracao = null;
        if (decoracaoId != null) {
            decoracao = decoracaoService.buscarPorId(decoracaoId);
        }

        TipoEvento tipoEvento = tipoEventoService.buscarPorId(tipoEventoId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        orcamento.setTipoEvento(tipoEvento);
        orcamento.setUsuario(usuario);
        orcamento.setDecoracao(decoracao);

        googleService.criarEvento(orcamento);
        return orcamentoRepository.save(orcamento);
    }

    public Orcamento atualizar(Orcamento orcamento, Integer tipoEventoId, Integer usuarioId, Integer decoracaoId, Authentication authentication) {
        if (orcamento.getStatus().equals("CANCELADO")){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Decoracao decoracao = null;
        if (decoracaoId != null) {
            decoracao = decoracaoService.buscarPorId(decoracaoId);
        }

        validarPermissaoOperacao(orcamento, authentication);

        TipoEvento tipoEvento = tipoEventoService.buscarPorId(tipoEventoId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        orcamento.setTipoEvento(tipoEvento);
        orcamento.setUsuario(usuario);
        orcamento.setDecoracao(decoracao);

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
    public Orcamento cancelarEvento(int id, Authentication authentication) {
        Orcamento orcamento = buscarPorId(id);

        if (orcamento.getCancelado() || orcamento.getStatus().equals("FINALIZADO") || orcamento.getStatus().equals("CONFIRMADO") ){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        validarPermissaoOperacao(orcamento, authentication);

        try {
            googleService.deletarEvento("primary", orcamento);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        orcamento.setId(id);
        orcamento.setCancelado(true);
        orcamento.setStatus("CANCELADO");
        return orcamentoRepository.save(orcamento);
    }

    private void validarPermissaoOperacao(Orcamento orcamento, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isUsuario = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));

        if (isAdmin) {
            return;
        }

        if (isUsuario) {
            long daysUntilEvent = ChronoUnit.DAYS.between(LocalDate.now(), orcamento.getDataEvento());
            if (daysUntilEvent < 7) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new RuntimeException("Usuário não autorizado para esta operação.");
        }
    }

    @Override
    public Orcamento confirmarEvento(int id) {
        Orcamento orcamento = buscarPorId(id);

        if (orcamento.getCancelado() || orcamento.getStatus().equals("FINALIZADO") ){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        orcamento.setId(id);
        orcamento.setStatus("CONFIRMADO");

        try {
            googleService.atualizarEvento("primary", orcamento);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        return orcamentoRepository.save(orcamento);
    }

    @Override
    public Orcamento confirmarDadosDoEvento(Orcamento orcamento, Integer tipoEventoId, Integer decoracaoId){
        if (orcamento.getStatus().equals("CANCELADO")){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Decoracao decoracao = null;
        if (decoracaoId != null) {
            decoracao = decoracaoService.buscarPorId(decoracaoId);
        }

        TipoEvento tipoEvento = tipoEventoService.buscarPorId(tipoEventoId);

        orcamento.setTipoEvento(tipoEvento);
        orcamento.setDecoracao(decoracao);

        try {
            googleService.atualizarEvento("primary", orcamento);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return orcamentoRepository.save(orcamento);

    }


    @Override
    public void finalizarOrcamentosExpirados() {
        List<Orcamento> orcamentos = orcamentoRepository.findByStatusAndDataEventoBefore("CONFIRMADO", LocalDate.now());

        for (Orcamento orcamento : orcamentos) {
            orcamento.finalizarSeDataPassou();

            try {
                googleService.atualizarEvento("primary", orcamento);
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException(e);
            }

            orcamentoRepository.save(orcamento);
        }
    }

    public Integer countByUsuarioId(Integer usuarioId){
        return orcamentoRepository.countByUsuarioId(usuarioId);
    }

    @Override
    public List<Orcamento> findByUsuarioId(int id) {
        return orcamentoRepository.findByUsuarioId(id);
    }

    public boolean isSevenDaysBeforeEvent(int id) {
        Orcamento orcamento = buscarPorId(id);

        LocalDate now = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(now, orcamento.getDataEvento());
        return daysDifference == 7;
    }

    public List<TipoEventoContagemDto> countOrcamentosByTipoEvento() {
        return orcamentoRepository.countOrcamentosByTipoEvento();
    }
}
