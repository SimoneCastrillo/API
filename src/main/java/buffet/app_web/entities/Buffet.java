package buffet.app_web.entities;

import buffet.app_web.enums.Plano;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Buffet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String imagem;
    private String descricao;
    private String email;
    private String nome;
    private String urlSite;
    private String telefone;
    private Plano plano;
}
