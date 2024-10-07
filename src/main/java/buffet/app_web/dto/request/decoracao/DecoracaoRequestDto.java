package buffet.app_web.dto.request.decoracao;

import buffet.app_web.entities.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DecoracaoRequestDto {
    @NotBlank
    private String nome;

    @NotBlank
    private String foto;

    @NotNull
    private TipoEvento tipoEvento;
}
