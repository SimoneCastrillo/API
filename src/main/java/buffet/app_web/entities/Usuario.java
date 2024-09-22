package buffet.app_web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @NotBlank
    @Size(min = 3, max = 30)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4)
    private String senha;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String telefone;

    private Integer tipo;
}
