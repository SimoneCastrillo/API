package buffet.app_web.dto.request.avaliacao;

import buffet.app_web.entities.TipoEvento;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class AvaliacaoRequestDto {
    private String nomeCliente;
    private String texto;
    private MultipartFile foto;
    private Integer tipoEventoId;
}
