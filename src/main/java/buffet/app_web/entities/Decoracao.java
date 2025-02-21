package buffet.app_web.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Base64;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Decoracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String foto;
    @ManyToOne
    private TipoEvento tipoEvento;
}
