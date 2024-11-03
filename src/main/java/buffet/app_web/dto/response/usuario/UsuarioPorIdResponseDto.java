package buffet.app_web.dto.response.usuario;

import buffet.app_web.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioPorIdResponseDto {
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Integer qtdOrcamento;
    private UserRole role;
    private String foto;
}
