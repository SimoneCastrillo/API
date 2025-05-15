package buffet.app_web.service;

import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.Usuario;
import buffet.app_web.entities.Usuario;
import buffet.app_web.enums.Plano;
import buffet.app_web.enums.UserRole;
import buffet.app_web.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class UsuarioServiceTest {
    @Mock // dublê - a nossa "repository" vai ser um dublê
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    // Vamos testar o método "buscarTodos"
    @Test
    @DisplayName("Dado que, não tenho nada no banco, retorna lista vazia")
    void buscarTodosListaVazia() {
        // GIVEN/ARRANGE - Configuração "chumbar" no código.
        List<Usuario> lista = Collections.emptyList();

        // WHEN/ARRANGE - quando qualquer código abaixo dessa linha,
        // chamar um método que tenha findAll sem sua lógica
        Mockito.when(usuarioRepository.findAll()).thenReturn(lista);

        // THEN/ACT
        // chama métodos que possível teriam findAll, ao serem chamados,
        // o findAll vai respeitar o comportamento configurado
        List<Usuario> resultado = usuarioService.listarTodos();

        // ASSERT/ASSERT
        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());

        // redundância, mas, outra maneira de verificar lista vazia
        assertEquals(0, resultado.size());

        Mockito.verify(usuarioRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho algo no banco, retorna lista com tipo de evento")
    void buscarTodosListaCheia() {
        // GIVEN
        Buffet buffet = new Buffet(7800000000L, "abc", "descricao", "corinthians@gmail.com", "Corinthians", "www.corinthians.com", "1177616231", Plano.PREMIUM);

        List<Usuario> usuarioss = List.of(
                new Usuario(
                        1,
                        "Eduardo",
                        "eduardo@gmail.com",
                        "123456",
                        "40028922",
                        UserRole.USUARIO,
                        null,
                        buffet
                ));

        // WHEN
        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarioss);

        // THEN
        List<Usuario> resultado = usuarioService.listarTodos();

        // ASSERT

        assertNotNull(resultado);
        assertEquals(usuarioss.size(), resultado.size());
//        assertEquals(usuarioss.get(0).getId(), resultado.get(0).getId());
//        assertEquals(usuarioss.get(1).getId(), resultado.get(1).getId());

        for (int i = 0; i < resultado.size(); i++) {

            Usuario usuariosEsperada = usuarioss.get(i);
            Usuario usuariosRetornada = resultado.get(i);

            assertEquals(usuariosEsperada.getId(), usuariosRetornada.getId());
            assertEquals(usuariosEsperada.getNome(), usuariosRetornada.getNome());
        }

        Mockito.verify(usuarioRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Dado que, tenho um usuário pelo id, retorne corretamente")
    void buscaPorIdCorretamente() {
        // GIVEN
        Buffet buffet = new Buffet(7800000000L, "abc", "descricao", "corinthians@gmail.com", "Corinthians", "www.corinthians.com", "1177616231", Plano.PREMIUM);

        Usuario usuariosEsperada = new Usuario(
                 1,
                 "Eduardo",
                "eduardo@gmail.com",
                "123456",
                "40028922",
                UserRole.USUARIO,
                null,
                buffet
        );

        // WHEN
        Mockito.when(usuarioRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(usuariosEsperada));

        // THEN
        Usuario resultado = usuarioService.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(usuariosEsperada.getId(), resultado.getId());

        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioRepository, Mockito.never()).findAll();
    }

    @Test
    @DisplayName("Dado que, o id não existe, retorna exception")
    void buscarPorIdIncorreto() {
        // GIVEN/WHEN/ARRANGE
        Mockito.when(usuarioRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        // THEN/ASSERT/ACT
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.buscarPorId(1));

        // ASSERT
        assertEquals("404 NOT_FOUND", ex.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
        Mockito.verify(usuarioRepository, Mockito.never()).findAll();
    }


    @Test
    @DisplayName("Dado que, o usuário não existe, salva corretamente")
    void salvarUsuarioCorretamente() {
        // GIVEN
        Buffet buffet = new Buffet(7800000000L, "abc", "descricao", "corinthians@gmail.com", "Corinthians", "www.corinthians.com", "1177616231", Plano.PREMIUM);

        Usuario usuarios = new Usuario(
                1,
                "Eduardo",
                "eduardo@gmail.com",
                "123456",
                "40028922",
                UserRole.USUARIO,
                null,
                buffet
        );

        // WHEN


        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class)))
                .thenReturn(usuarios);

        Mockito.when(usuarioRepository.findByEmail(Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito.when(passwordEncoder.encode(Mockito.any()))
                .thenReturn("");

        // THEN
        Usuario resultado = usuarioService.salvar(usuarios, buffet.getId());

        // ASSERT
        assertNotNull(resultado);
        assertEquals(usuarios.getId(), resultado.getId());

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuarios);
    }
}