package buffet.app_web.dto.request.tipoevento;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoEventoRequestDto {
    @NotBlank
    private String nome;
}
