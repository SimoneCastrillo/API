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
    private String sugestao;
    @ManyToOne
    private TipoEvento tipoEvento;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Decoracao decoracao;

    public String getStatus(){
        if (this.cancelado){
            return "CANCELADO";
        }
        if (this.dataEvento.isBefore(LocalDate.now())){
            return "FINALIZADO";
        }
        return "ABERTO";
    }
}
