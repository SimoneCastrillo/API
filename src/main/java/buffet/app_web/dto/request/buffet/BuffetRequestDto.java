package buffet.app_web.dto.request.buffet;

import buffet.app_web.enums.Plano;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BuffetRequestDto {
    private MultipartFile imagem;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    private String urlSite;
    @NotBlank
    private String telefone;
    @NotNull
    private Plano plano;
}
