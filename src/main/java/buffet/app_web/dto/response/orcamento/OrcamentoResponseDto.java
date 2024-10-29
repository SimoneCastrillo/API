package buffet.app_web.dto.response.orcamento;

import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.entities.Usuario;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
public class OrcamentoResponseDto {
    private Integer id;
    private LocalDate dataEvento;
    private Integer qtdConvidados;
    private String status;
    private Boolean cancelado;
    private LocalTime inicio;
    private LocalTime fim;
    private String saborBolo;
    private String pratoPrincipal;
    private String sugestao;
    private TipoEventoDto tipoEvento;
    private UsuarioDto usuario;
    private DecoracaoDto decoracao;

    @Data
    @Builder
    public static class TipoEventoDto{
        private Integer id;
        private String nome;
    }

    @Data
    @Builder
    public static class UsuarioDto{private Integer id;
        private String nome;
        private String email;
        private String senha;
        private String telefone;
        private String foto;
    }

    @Data
    @Builder
    public static class DecoracaoDto{
        private Integer id;
        private String nome;
        private DecoracaoResponseDto.TipoEventoDto tipoEvento;
        private String foto;
    }
}
