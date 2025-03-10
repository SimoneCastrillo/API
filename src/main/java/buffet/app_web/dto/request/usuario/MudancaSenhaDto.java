package buffet.app_web.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MudancaSenhaDto {
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    @Size(min = 4)
    private String novaSenha;
    @NotBlank
    private String confirmacaoNovaSenha;
}
