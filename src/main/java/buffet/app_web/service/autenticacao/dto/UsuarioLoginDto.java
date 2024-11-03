package buffet.app_web.service.autenticacao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioLoginDto {

    private String email;

    private String senha;
}
