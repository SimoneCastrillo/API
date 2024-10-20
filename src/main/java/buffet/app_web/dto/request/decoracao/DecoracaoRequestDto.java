package buffet.app_web.dto.request.decoracao;

import buffet.app_web.entities.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class DecoracaoRequestDto {
    @NotBlank
    private String nome;


    private MultipartFile foto;

    @NotNull
    private TipoEvento tipoEvento;
}
