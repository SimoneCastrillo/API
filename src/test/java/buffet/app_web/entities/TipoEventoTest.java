package buffet.app_web.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("1. [ESTRUTURA] - Teste de Estrutura da Classe TipoEvento")

class TipoEventoTest {

    @Test
    @DisplayName("Deve garantir que a classe TipoEvento tenha métodos  getter e setter para todos os atributos")
    void deveGarantirGettersSetters(){
        try{
            Method getId = TipoEvento.class.getMethod("getId");
            Method setId = TipoEvento.class.getMethod("setId", Integer.class);

            Method getNome = TipoEvento.class.getMethod("getNome");
            Method setNome = TipoEvento.class.getMethod("setNome", String.class);

            assertNotNull(getId, "O método getId() deve existir.");
            assertNotNull(setId, "O método setId() deve existir.");

            assertNotNull(getNome, "O método getNome() deve existir.");
            assertNotNull(setNome, "O método setNome() deve existir.");

        } catch (NoSuchMethodException e) {
            fail("A classe TipoEvento deve conter os métodos getter e setter para todos os atributos.");
        }
    }
}