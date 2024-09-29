package buffet.app_web.dto.response;

import buffet.app_web.entities.TipoEvento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DecoracaoResponseDto {
    private Integer id;
    private String nome;
    private String foto;
    private TipoEvento tipoEvento;
}
