package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumoFinanceiroDto {
    private Double lucroTotal;
    private Double faturamentoTotal;
    private Double despesaTotal;
}
