package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LucroPorTipoEventoDto {
    private String tipoEvento;
    private Double lucro;
}
