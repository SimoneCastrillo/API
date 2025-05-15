package buffet.app_web.dto.response.decoracao;

import buffet.app_web.dto.response.avaliacao.AvaliacaoResponseDto;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.enums.Plano;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DecoracaoResponseDto {
    private Integer id;
    private String nome;
    private TipoEventoDto tipoEvento;
    private String foto;
    private BuffetResponseDto buffet;

    @Data
    @Builder
    public static class TipoEventoDto{
        private Integer id;
        private String nome;
    }

    @Data
    @Builder
    public static class BuffetResponseDto {
        private Long id;
        private String imagem;
        private String nome;
        private String descricao;
        private String email;
        private String urlSite;
        private String telefone;
        private Plano plano;
    }
}
