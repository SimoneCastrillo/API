package buffet.app_web.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UsuarioRequestDto {
    @NotBlank
    @Size(min = 3, max = 30)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4)
    private String senha;
    private MultipartFile foto;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String telefone;
}
