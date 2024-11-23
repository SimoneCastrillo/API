package buffet.app_web.entities;

import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoTest {

    @Test
    @DisplayName("Deve garantir que a classe Avaliacao tenha métodos  getter e setter para todos os atributos")
    void deveGarantirGettersSetters() {
        try {
            Method getId = Avaliacao.class.getMethod("getId");
            Method setId = Avaliacao.class.getMethod("setId", Integer.class);

            Method getNomeCliente = Avaliacao.class.getMethod("getNomeCliente");
            Method setNomeCliente = Avaliacao.class.getMethod("setNomeCliente", String.class);

            Method getTexto = Avaliacao.class.getMethod("getTexto");
            Method setTexto = Avaliacao.class.getMethod("setTexto", String.class);

            assertNotNull(getId, "O método getId() deve existir.");
            assertNotNull(setId, "O método setId() deve existir.");

            assertNotNull(getNomeCliente, "O método getNomeCliente() deve existir.");
            assertNotNull(setNomeCliente, "O método setNomeCliente() deve existir.");

            assertNotNull(getTexto, "O método getTexto() deve existir.");
            assertNotNull(setTexto, "O método setTexto() deve existir.");

        } catch (NoSuchMethodException e) {
            fail("A classe Avaliacao deve conter os métodos getter e setter para todos os atributos.");
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
