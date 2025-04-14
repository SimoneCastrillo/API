package buffet.app_web.service;

import buffet.app_web.entities.*;
import buffet.app_web.enums.Plano;
import buffet.app_web.enums.UserRole;
import buffet.app_web.repositories.OrcamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrcamentoServiceTest {

    @Mock
    private OrcamentoRepository orcamentoRepository;

    @Mock
    private TipoEventoService tipoEventoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private DecoracaoService decoracaoService;

    @Mock
    private BuffetService buffetService;

    @Mock
    private GoogleService googleService;

    @InjectMocks
    private OrcamentoService orcamentoService;

    @Test
    @DisplayName("Dado que, não tenho nada no banco, retorna lista vazia")
    void buscarTodosListaVazia() {
        // GIVEN
        List<Orcamento> lista = Collections.emptyList();

        // WHEN
        Mockito.when(orcamentoRepository.findAll()).thenReturn(lista);

        // THEN
        List<Orcamento> resultado = orcamentoService.listarTodos();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.size());

        Mockito.verify(orcamentoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho algo no banco, retorna lista com orçamentos")
    void buscarTodosListaCheia() {
        // GIVEN
        TipoEvento tipoEvento = new TipoEvento(1, "Rave");
        Usuario usuario = new Usuario(1, "Fernanda", "fernanda@email.com", "123456", "000", UserRole.USUARIO, null);
        Decoracao decoracao = new Decoracao(1, "Minimalista", "abc", tipoEvento);
        Buffet buffet = new Buffet(7800000000L, "abc", "descricao", "corinthians@gmail.com", "Corinthians", "www.corinthians.com", "1177616231", Plano.PREMIUM);
        Endereco endereco = new Endereco(780000000L, "rua 1", "1", "Buffet", "Consolação", "São Paulo", "SP", "06123181", buffet);

        List<Orcamento> orcamentos = List.of(
                new Orcamento(1, LocalDate.now(), 50, "Confirmado", false,
                        LocalTime.of(18, 0), LocalTime.of(23, 0), "Chocolate",
                        "Frango assado", 500.0, 1000.0, 500.0, "Tudo certo",
                        "google-event-123", tipoEvento, usuario, decoracao, buffet, endereco)
        );

        // WHEN
        Mockito.when(orcamentoRepository.findAll()).thenReturn(orcamentos);

        // THEN
        List<Orcamento> resultado = orcamentoService.listarTodos();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(orcamentos.size(), resultado.size());

        for (int i = 0; i < resultado.size(); i++) {
            Orcamento esperado = orcamentos.get(i);
            Orcamento retornado = resultado.get(i);

            assertEquals(esperado.getId(), retornado.getId());
            assertEquals(esperado.getDataEvento(), retornado.getDataEvento());
        }

        Mockito.verify(orcamentoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, o id existe, retorne o orçamento corretamente")
    void buscarPorIdCorretamente() {
        // GIVEN
        // GIVEN
        TipoEvento tipoEvento = new TipoEvento(1, "Rave");
        Usuario usuario = new Usuario(1, "Fernanda", "fernanda@email.com", "123456", "000", UserRole.USUARIO, null);
        Decoracao decoracao = new Decoracao(1, "Minimalista", "abc", tipoEvento);
        Buffet buffet = new Buffet(7800000000L, "abc", "descricao", "corinthians@gmail.com", "Corinthians", "www.corinthians.com", "1177616231", Plano.PREMIUM);
        Endereco endereco = new Endereco(780000000L, "rua 1", "1", "Buffet", "Consolação", "São Paulo", "SP", "06123181", buffet);

        Orcamento orcamento = new Orcamento(1, LocalDate.now(), 50, "Confirmado", false,
                LocalTime.of(18, 0), LocalTime.of(23, 0),
                "Chocolate", "Frango assado", 500.0, 1000.0,
                500.0, "Tudo certo", "google-event-123",
                tipoEvento, usuario, decoracao, buffet, endereco);

        // WHEN
        Mockito.when(orcamentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(orcamento));

        // THEN
        Orcamento resultado = orcamentoService.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(orcamento.getId(), resultado.getId());

        Mockito.verify(orcamentoRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("Dado que, o id não existe, lança exceção")
    void buscarPorIdIncorreto() {
        // GIVEN
        Mockito.when(orcamentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // THEN
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> orcamentoService.buscarPorId(1));

        // ASSERT
        assertEquals("404 NOT_FOUND", ex.getMessage());
        Mockito.verify(orcamentoRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("Dado que, o orçamento é novo, salva corretamente")
    void salvarOrcamentoCorretamente() {
        // GIVEN
        // GIVEN
        TipoEvento tipoEvento = new TipoEvento(1, "Rave");
        Usuario usuario = new Usuario(1, "Fernanda", "fernanda@email.com", "123456", "000", UserRole.USUARIO, null);
        Decoracao decoracao = new Decoracao(1, "Minimalista", "abc", tipoEvento);
        Buffet buffet = new Buffet(7800000000L, "abc", "descricao", "corinthians@gmail.com", "Corinthians", "www.corinthians.com", "1177616231", Plano.PREMIUM);
        Endereco endereco = new Endereco(780000000L, "rua 1", "1", "Buffet", "Consolação", "São Paulo", "SP", "06123181", buffet);

        Orcamento orcamento = new Orcamento(1, LocalDate.now(), 50, "Confirmado", false,
                LocalTime.of(18, 0), LocalTime.of(23, 0),
                "Chocolate", "Frango assado", 500.0, 1000.0,
                500.0, "Tudo certo", "google-event-123",
                tipoEvento, usuario, decoracao, buffet, endereco);

        // WHEN
        Mockito.when(orcamentoRepository.save(Mockito.any(Orcamento.class))).thenReturn(orcamento);

        Mockito.when(tipoEventoService.buscarPorId(Mockito.anyInt()))
                .thenReturn(tipoEvento);

        Mockito.when(usuarioService.buscarPorId(Mockito.anyInt()))
                .thenReturn(usuario);

        Mockito.when(buffetService.buscarPorId(Mockito.anyLong()))
                .thenReturn(buffet);

        Mockito.when(decoracaoService.buscarPorId(Mockito.anyInt()))
                .thenReturn(decoracao);


        // THEN
        Orcamento resultado = orcamentoService.salvar(orcamento, tipoEvento.getId(), usuario.getId(), decoracao.getId(), buffet.getId(), endereco.getId());

        // ASSERT
        assertNotNull(resultado);
        assertEquals(orcamento.getId(), resultado.getId());

        Mockito.verify(orcamentoRepository, Mockito.times(1)).save(orcamento);

        Mockito.verify(googleService, Mockito.times(1)).criarEvento(orcamento);
    }
}
