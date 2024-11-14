package buffet.app_web.dto.response.tipoevento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoEventoContagemDto {
    private String nome;
    private Long quantidade;
}
