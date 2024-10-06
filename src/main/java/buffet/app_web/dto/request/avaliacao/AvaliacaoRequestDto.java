package buffet.app_web.dto.request.avaliacao;

import buffet.app_web.entities.TipoEvento;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvaliacaoRequestDto {
    private String nomeCliente;
    private String texto;
    private TipoEvento tipoEvento;
}
