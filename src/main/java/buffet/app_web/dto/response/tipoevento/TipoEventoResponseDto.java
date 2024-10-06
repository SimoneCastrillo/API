package buffet.app_web.dto.response.tipoevento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoEventoResponseDto {
    private Integer id;
    private String nome;
}
