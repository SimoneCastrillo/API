package buffet.app_web.entities;

import buffet.app_web.enums.UserRole;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    @Test
    @DisplayName("Deve garantir que a classe Avaliacao tenha métodos  getter e setter para todos os atributos")
    void deveGarantirGettersSetters() {
        try {
            Method getId = Usuario.class.getMethod("getId");
            Method setId = Usuario.class.getMethod("setId", Integer.class);

            Method getNome = Usuario.class.getMethod("getNome");
            Method setNome = Usuario.class.getMethod("setNome", String.class);

            Method getEmail = Usuario.class.getMethod("getEmail");
            Method setEmail = Usuario.class.getMethod("setEmail", String.class);

            Method getSenha = Usuario.class.getMethod("getSenha");
            Method setSenha = Usuario.class.getMethod("setSenha", String.class);

            Method getTelefone = Usuario.class.getMethod("getTelefone");
            Method setTelefone = Usuario.class.getMethod("setTelefone", String.class);

            Method getRole = Usuario.class.getMethod("getRole");
            Method setRole = Usuario.class.getMethod("setRole", UserRole.class);

            Method getFoto = Usuario.class.getMethod("getFoto");
            Method setFoto = Usuario.class.getMethod("setFoto", String.class);

            assertNotNull(getId, "O método getId() deve existir.");
            assertNotNull(setId, "O método setId() deve existir.");

            assertNotNull(getNome, "O método getNome() deve existir.");
            assertNotNull(setNome, "O método setNome() deve existir.");

            assertNotNull(getEmail, "O método getEmail() deve existir.");
            assertNotNull(setEmail, "O método setEmail() deve existir.");

            assertNotNull(getSenha, "O método getSenha() deve existir.");
            assertNotNull(setSenha, "O método setSenha() deve existir.");

            assertNotNull(getTelefone, "O método getTelefone() deve existir.");
            assertNotNull(setTelefone, "O método setTelefone() deve existir.");

            assertNotNull(getRole, "O método getRole() deve existir.");
            assertNotNull(setRole, "O método setRole() deve existir.");

            assertNotNull(getFoto, "O método getFoto() deve existir.");
            assertNotNull(setFoto, "O método setFoto() deve existir.");

        } catch (NoSuchMethodException e) {
            fail("A classe Usuario deve conter os métodos getter e setter para todos os atributos.");
        }
    }

    @Test
    @DisplayName("Deve garantir que o atributo 'avaliacao' tenha uma anotação de relacionamento")
    void deveGarantirAnotacaoDeRelacionamentoEmAvaliacao() {
        try {
            Field avaliacaoField = Avaliacao.class.getDeclaredField("tipoEvento");
            assertTrue(avaliacaoField.isAnnotationPresent(ManyToOne.class),
                    "O campo 'avaliacao' deve ter uma anotação de relacionamento.");
        } catch (NoSuchFieldException e) {
            fail("O atributo 'avaliacao' deve existir na classe Avaliacao.");
        }
    }

}
