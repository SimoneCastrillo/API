package buffet.app_web.dto.response.usuario;

import buffet.app_web.enums.Plano;
import buffet.app_web.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDto {
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private UserRole role;
    private String foto;
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
