package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaturamentoDespesaTipoEventoDto {
    private String tipoEvento;
    private Double faturamento;
    private Double despesa;
}
