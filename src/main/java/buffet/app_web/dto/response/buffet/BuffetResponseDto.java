package buffet.app_web.dto.response.buffet;

import buffet.app_web.enums.Plano;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BuffetResponseDto {
    private Long id;
    private MultipartFile imagem;
    private String nome;
    private String urlSite;
    private String telefone;
    private Plano plano;
}
