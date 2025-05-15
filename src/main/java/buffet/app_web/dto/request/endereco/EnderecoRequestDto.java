package buffet.app_web.dto.request.endereco;

import buffet.app_web.entities.Buffet;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoRequestDto {
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Long buffetId;
}
