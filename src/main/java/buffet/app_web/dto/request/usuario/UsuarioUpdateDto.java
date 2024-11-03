package buffet.app_web.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UsuarioUpdateDto {
    @Size(min = 3, max = 30)
    private String nome;

    @Email
    private String email;

    private MultipartFile foto;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String telefone;
}
