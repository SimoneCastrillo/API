package buffet.app_web.controllers;

import buffet.app_web.entities.Usuario;
import buffet.app_web.service.UsuarioService;
import buffet.app_web.strategies.UsuarioStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
public class UsuarioController {
    @Autowired
    private UsuarioStrategy usuarioStrategy;

    @Operation(summary = "Listar todos os usuários", description = """
            # Listar todos os usuários
            ---
            O endpoint lista todos os usuários cadastrados.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista todos os usuários",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Quando não tem usuários cadastrados",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        if (usuarioStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarioStrategy.listarTodos());
    }

    @Operation(summary = "Buscar um usuário por ID", description = """
            # Buscar usuário
            ---
            Retorna o usuário correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content()
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id){
        Optional<Usuario> usuarioOpt = usuarioStrategy.buscarPorId(id);

        if (usuarioOpt.isPresent()){
            return ResponseEntity.status(200).body(usuarioOpt.get());
        }

        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Criar um novo usuário", description = """
            # Criar usuário
            ---
            Cria e retorna o novo usuário.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody @Valid Usuario usuario){
        usuario.setId(null);
        return ResponseEntity.status(201).body(usuarioStrategy.salvar(usuario));
    }

    @Operation(summary = "Atualizar um usuário", description = """
            # Atualizar usuário
            ---
            Atualiza o usuário correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content()
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable int id, @RequestBody @Valid Usuario usuarioAtualizado){
        if(usuarioStrategy.buscarPorId(id).isPresent()){
            usuarioAtualizado.setId(id);
            usuarioStrategy.salvar(usuarioAtualizado);

            return ResponseEntity.status(200).body(usuarioAtualizado);
        }

        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Deletar um usuário", description = """
            # Deletar usuário
            ---
            Deleta o usuário correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content()
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if(usuarioStrategy.buscarPorId(id).isPresent()){
            usuarioStrategy.deletar(id);

            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
