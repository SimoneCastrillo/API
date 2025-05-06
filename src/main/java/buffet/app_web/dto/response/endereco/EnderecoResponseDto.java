package buffet.app_web.dto.response.endereco;

import buffet.app_web.enums.Plano;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoResponseDto {
    private Long id;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private BuffetDto buffetId;

    @Data
    @Builder
    public static class BuffetDto{
        private Long id;
        private String imagem;
        private String nome;
        private String email;
        private String urlSite;
        private String telefone;
        private Plano plano;
    }
}
