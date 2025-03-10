package buffet.app_web.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodigoVerficacaoDto {
    @NotBlank(message = "Código de verificação é obrigatório")
    private String codigo;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
}
