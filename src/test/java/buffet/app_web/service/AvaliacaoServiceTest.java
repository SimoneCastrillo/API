package buffet.app_web.service;

import buffet.app_web.entities.Avaliacao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.enums.UserRole;
import buffet.app_web.repositories.AvaliacaoRepository;
import buffet.app_web.repositories.TipoEventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock // dublê - a nossa "repository" vai ser um dublê
    private AvaliacaoRepository avaliacaoRepository;

   @InjectMocks
    private AvaliacaoService avaliacaoService;

   @Mock
   private TipoEventoService tipoEventoService;

    // Vamos testar o método "buscarTodos"
    @Test
    @DisplayName("Dado que, não tenho nada no banco, retorna lista vazia")
    void buscarTodosListaVazia() {
        // GIVEN/ARRANGE - Configuração "chumbar" no código.
        List<Avaliacao> lista = Collections.emptyList();

        // WHEN/ARRANGE - quando qualquer código abaixo dessa linha,
        // chamar um método que tenha findAll sem sua lógica
        Mockito.when(avaliacaoRepository.findAll()).thenReturn(lista);

        // THEN/ACT
        // chama métodos que possível teriam findAll, ao serem chamados,
        // o findAll vai respeitar o comportamento configurado
        List<Avaliacao> resultado = avaliacaoService.listarTodos();

        // ASSERT/ASSERT
        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());

        // redundância, mas, outra maneira de verificar lista vazia
        assertEquals(0, resultado.size());

        Mockito.verify(avaliacaoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho algo no banco, retorna lista com tipo de evento")
    void buscarTodosListaCheia() {
        // GIVEN
        TipoEvento tiposEvento = new TipoEvento(
                1,
                "Rave"
        );

        List<Avaliacao> avaliacao = List.of(
                new Avaliacao(
                        1,
                "Fernanda",
                "Olá, tudo bem?",
                tiposEvento,
                null
                ));

        // WHEN
        Mockito.when(avaliacaoRepository.findAll()).thenReturn(avaliacao);

        // THEN
        List<Avaliacao> resultado = avaliacaoService.listarTodos();

        // ASSERT

        assertNotNull(resultado);
        assertEquals(avaliacao.size(), resultado.size());
//        assertEquals(avaliacaos.get(0).getId(), resultado.get(0).getId());
//        assertEquals(avaliacaos.get(1).getId(), resultado.get(1).getId());

        for (int i = 0; i < resultado.size(); i++) {

            Avaliacao avaliacaoEsperada = avaliacao.get(i);
            Avaliacao avaliacaoRetornada = resultado.get(i);

            assertEquals(avaliacaoEsperada.getId(), avaliacaoRetornada.getId());
            assertEquals(avaliacaoEsperada.getNomeCliente(), avaliacaoRetornada.getNomeCliente());
        }

        Mockito.verify(avaliacaoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho uma avaliação pelo id, retorne corretamente")
    void buscaPorIdCorretamente() {
        // GIVEN
        TipoEvento tiposEvento = new TipoEvento(
                1,
                "Rave"
        );

        Avaliacao avaliacao = new Avaliacao(
                1,
                "Fernanda",
                "Olá, tudo bem?",
                tiposEvento,
                null
        );

        // WHEN
        Mockito.when(avaliacaoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(avaliacao));

        // THEN
        Avaliacao resultado = avaliacaoService.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(avaliacao.getId(), resultado.getId());

        Mockito.verify(avaliacaoRepository, Mockito.times(1)).findById(1);
        Mockito.verify(avaliacaoRepository, Mockito.never()).findAll();
    }

    @Test
    @DisplayName("Dado que, o id não existe, retorna exception")
    void buscarPorIdIncorreto() {
        // GIVEN/WHEN/ARRANGE
        Mockito.when(avaliacaoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        // THEN/ASSERT/ACT
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> avaliacaoService.buscarPorId(1));

        // ASSERT
        assertEquals("404 NOT_FOUND", ex.getMessage());
        Mockito.verify(avaliacaoRepository, Mockito.times(1)).findById(1);
        Mockito.verify(avaliacaoRepository, Mockito.never()).findAll();
    }


    @Test
    @DisplayName("Dado que, a avaliação não existe, salva corretamente")
    void salvarAvaliacaoCorretamente() {
        // GIVEN
        TipoEvento tiposEvento = new TipoEvento(
                1,
                "Rave"
        );

        Avaliacao avaliacao = new Avaliacao(
                1,
                "Fernanda",
                "Olá, tudo bem?",
                tiposEvento,
                null
        );

        // WHEN


        Mockito.when(avaliacaoRepository.save(Mockito.any(Avaliacao.class)))
                .thenReturn(avaliacao);
        Mockito.when(tipoEventoService.buscarPorId(Mockito.anyInt()))
                .thenReturn(tiposEvento);

        // THEN
        Avaliacao resultado = avaliacaoService.salvar(avaliacao, tiposEvento.getId());

        // ASSERT
        assertNotNull(resultado);
        assertEquals(avaliacao.getId(), resultado.getId());

        Mockito.verify(avaliacaoRepository, Mockito.times(1)).save(avaliacao);
    }
}