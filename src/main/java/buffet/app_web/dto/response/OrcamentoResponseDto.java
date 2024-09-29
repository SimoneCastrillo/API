package buffet.app_web.dto.response;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.entities.Usuario;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrcamentoResponseDto {
    private Integer id;
    private LocalDate dataEvento;
    private Integer qtdConvidados;
    private String status;
    private LocalTime inicio;
    private LocalTime fim;
    private String sugestao;
    private TipoEvento tipoEvento;
    private Usuario usuario;
    private Decoracao decoracao;
}
