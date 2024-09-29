package buffet.app_web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@Entity
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Future
    private LocalDate dataEvento;

    @Max(value = 180)
    private Integer qtdConvidados;
    private String status;
    private LocalTime inicio;
    private LocalTime fim;
    private String sugestao;

    @ManyToOne
    private TipoEvento tipoEvento;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Decoracao decoracao;
}
