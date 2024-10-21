package buffet.app_web.dto.response.decoracao;

import buffet.app_web.dto.response.avaliacao.AvaliacaoResponseDto;
import buffet.app_web.entities.TipoEvento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DecoracaoResponseDto {
    private Integer id;
    private String nome;
    private TipoEventoDto tipoEvento;
    private String foto;

    @Data
    @Builder
    public static class TipoEventoDto{
        private Integer id;
        private String nome;
    }
}
