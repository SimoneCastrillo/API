package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaturamentoDespesaMesDto {
    private int mes;
    private Double faturamento;
    private Double despesa;
}
