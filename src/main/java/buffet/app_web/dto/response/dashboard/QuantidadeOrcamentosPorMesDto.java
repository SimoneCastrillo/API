package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuantidadeOrcamentosPorMesDto {
    private int mes;
    private long quantidade;
}
