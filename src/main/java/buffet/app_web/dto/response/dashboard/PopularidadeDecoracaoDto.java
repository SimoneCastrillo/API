package buffet.app_web.dto.response.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PopularidadeDecoracaoDto {
    private String decoracao;
    private Long quantidadeEventos;
    private Double lucroMedio;
    private Long frequencia;
}
