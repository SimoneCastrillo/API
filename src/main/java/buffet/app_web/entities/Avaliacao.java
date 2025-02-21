package buffet.app_web.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeCliente;
    private String texto;
    @ManyToOne
    private TipoEvento tipoEvento;
    @Column(columnDefinition = "TEXT")
    private String foto;
}
