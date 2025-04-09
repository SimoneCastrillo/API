package buffet.app_web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dataEvento;
    private Integer qtdConvidados;
    private String status;
    private Boolean cancelado;
    private LocalTime inicio;
    private LocalTime fim;
    private String saborBolo;
    private String pratoPrincipal;
    private Double lucro;
    private Double faturamento;
    private Double despesa;
    private String sugestao;
    private String googleEventoId;
    @ManyToOne
    private TipoEvento tipoEvento;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "decoracao_id", nullable = true)
    private Decoracao decoracao;
    @ManyToOne
    private Buffet buffet;

    public void finalizarSeDataPassou() {
        if (this.status.equals("CONFIRMADO") && this.dataEvento.isBefore(LocalDate.now())) {
            this.status = "FINALIZADO";
        }
    }
}
