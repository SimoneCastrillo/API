package buffet.app_web.service;

import buffet.app_web.entities.TipoEvento;
import buffet.app_web.repositories.TipoEventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class TipoEventoServiceTest {
    @Mock // dublê - a nossa "repository" vai ser um dublê
    private TipoEventoRepository tipoEventoRepository;

    @InjectMocks
    private TipoEventoService tipoEventoService;

    // Vamos testar o método "buscarTodos"
    @Test
    @DisplayName("Dado que, não tenho nada no banco, retorna lista vazia")
    void buscarTodosListaVazia() {
        // GIVEN/ARRANGE - Configuração "chumbar" no código.
        List<TipoEvento> lista = Collections.emptyList();

        // WHEN/ARRANGE - quando qualquer código abaixo dessa linha,
        // chamar um método que tenha findAll sem sua lógica
        Mockito.when(tipoEventoRepository.findAll()).thenReturn(lista);

        // THEN/ACT
        // chama métodos que possível teriam findAll, ao serem chamados,
        // o findAll vai respeitar o comportamento configurado
        List<TipoEvento> resultado = tipoEventoService.listarTodos();

        // ASSERT/ASSERT
        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());

        // redundância, mas, outra maneira de verificar lista vazia
        assertEquals(0, resultado.size());

        Mockito.verify(tipoEventoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho algo no banco, retorna lista com tipo de evento")
    void buscarTodosListaCheia() {
        // GIVEN
        List<TipoEvento> tiposEvento = List.of(
                new TipoEvento(
                        1,
                        "Rave"
                ), new TipoEvento(
                        2,
                        "Jazz"
                ));

        // WHEN
        Mockito.when(tipoEventoRepository.findAll()).thenReturn(tiposEvento);

        // THEN
        List<TipoEvento> resultado = tipoEventoService.listarTodos();

        // ASSERT

        assertNotNull(resultado);
        assertEquals(tiposEvento.size(), resultado.size());
//        assertEquals(tiposEvento.get(0).getId(), resultado.get(0).getId());
//        assertEquals(tiposEvento.get(1).getId(), resultado.get(1).getId());

        for (int i = 0; i < resultado.size(); i++) {

            TipoEvento tipoEventoEsperado = tiposEvento.get(i);
            TipoEvento tipoEventoRetornada = resultado.get(i);

            assertEquals(tipoEventoEsperado.getId(), tipoEventoRetornada.getId());
            assertEquals(tipoEventoEsperado.getNome(), tipoEventoRetornada.getNome());
        }

        Mockito.verify(tipoEventoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho um tipo de evento pelo id, retorne corretamente")
    void buscaPorIdCorretamente() {
        // GIVEN
        TipoEvento tipoEventoEsperado = new TipoEvento(
                1,
                "Rave"
        );

        // WHEN
        Mockito.when(tipoEventoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(tipoEventoEsperado));

        // THEN
        TipoEvento resultado = tipoEventoService.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(tipoEventoEsperado.getId(), resultado.getId());

        Mockito.verify(tipoEventoRepository, Mockito.times(1)).findById(1);
        Mockito.verify(tipoEventoRepository, Mockito.never()).findAll();
    }

    @Test
    @DisplayName("Dado que, o id não existe, retorna exception")
    void buscarPorIdIncorreto() {
        // GIVEN/WHEN/ARRANGE
        Mockito.when(tipoEventoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        // THEN/ASSERT/ACT
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> tipoEventoService.buscarPorId(1));

        // ASSERT
        assertEquals("404 NOT_FOUND", ex.getMessage());
        Mockito.verify(tipoEventoRepository, Mockito.times(1)).findById(1);
        Mockito.verify(tipoEventoRepository, Mockito.never()).findAll();
    }


    @Test
    @DisplayName("Dado que, o tipo de evento não existe, salva corretamente")
    void salvarTipoEventoCorretamente() {
        // GIVEN
        TipoEvento tiposEvento = new TipoEvento(
                1,
                "Rave"
        );

        // WHEN


        Mockito.when(tipoEventoRepository.save(Mockito.any(TipoEvento.class)))
                .thenReturn(tiposEvento);

        // THEN
        TipoEvento resultado = tipoEventoService.salvar(tiposEvento);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(tiposEvento.getId(), resultado.getId());

        Mockito.verify(tipoEventoRepository, Mockito.times(1)).save(tiposEvento);
    }

}