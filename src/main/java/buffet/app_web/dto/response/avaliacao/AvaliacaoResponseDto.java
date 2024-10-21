package buffet.app_web.dto.response.avaliacao;

import buffet.app_web.entities.TipoEvento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvaliacaoResponseDto {
    private Integer id;
    private String nomeCliente;
    private String texto;
    private TipoEventoDto tipoEvento;
    private String foto;

    @Data
    @Builder
    public static class TipoEventoDto {
        private Integer id;
        private String nome;
    }
}
