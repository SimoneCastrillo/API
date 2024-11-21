package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopEventoDto {
    private String tipoEvento;
    private Double valor;
}
