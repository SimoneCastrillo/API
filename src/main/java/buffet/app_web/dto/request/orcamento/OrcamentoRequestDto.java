package buffet.app_web.dto.request.orcamento;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.entities.Usuario;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class OrcamentoRequestDto {
    @NotNull
    @Future
    private LocalDate dataEvento;

    @NotNull
    @Max(value = 180)
    private Integer qtdConvidados;
    @NotNull
    private LocalTime inicio;
    private LocalTime fim;
    private String sugestao;
    @NotNull
    private TipoEvento tipoEvento;
    @NotNull
    private Usuario usuario;
    @NotNull
    private Decoracao decoracao;
}
