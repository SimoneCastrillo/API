package buffet.app_web.dto.response.tipoevento;

import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.enums.Plano;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoEventoResponseDto {
    private Integer id;
    private String nome;
    private BuffetResponseDto buffet;

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
