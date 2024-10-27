package buffet.app_web.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UsuarioCriacaoDto {
    @NotBlank
    @Size(min = 3, max = 30)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4)
    private String senha;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String telefone;
}
