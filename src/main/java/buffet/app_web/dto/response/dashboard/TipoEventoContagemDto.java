package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoEventoContagemDto {
    private String nome;
    private Long quantidade;
}
