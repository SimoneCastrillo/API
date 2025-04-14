package buffet.app_web.dto.request.orcamento;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.entities.Usuario;
import jakarta.persistence.Column;
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
    private String sugestao;
    @NotNull
    private Integer tipoEventoId;
    @NotNull
    private Integer usuarioId;
    @Column(nullable = true)
    private Integer decoracaoId;
    @NotNull
    private Long buffetId;
    @NotNull
    private Long enderecoId;
}
