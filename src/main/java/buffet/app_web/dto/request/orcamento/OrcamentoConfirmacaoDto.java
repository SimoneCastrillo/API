package buffet.app_web.dto.request.orcamento;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class OrcamentoConfirmacaoDto {
    @NotNull
    @Future
    private LocalDate dataEvento;

    @NotNull
    @Max(value = 180)
    private Integer qtdConvidados;
    @NotNull
    private LocalTime inicio;
    @NotNull
    private LocalTime fim;
    private String saborBolo;
    private String pratoPrincipal;
    private Double lucro;
    private Double faturamento;
    private Double despesa;
    private String sugestao;
    @NotNull
    private Integer tipoEventoId;
    @Column(nullable = true)
    private Integer decoracaoId;
    private Integer buffetId;
    private Integer enderecoId;
}
