package buffet.app_web.controllers;

import buffet.app_web.dto.request.usuario.UsuarioRequestDto;
import buffet.app_web.dto.response.usuario.UsuarioResponseDto;
import buffet.app_web.entities.Usuario;
import buffet.app_web.mapper.UsuarioMapper;
import buffet.app_web.service.autenticacao.dto.UsuarioLoginDto;
import buffet.app_web.service.autenticacao.dto.UsuarioTokenDto;
import buffet.app_web.strategies.UsuarioStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

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
    public ResponseEntity<List<UsuarioResponseDto>> listarTodos(){
        if (usuarioStrategy.listarTodos().isEmpty()){
            return status(204).build();
        }

        List<UsuarioResponseDto> listaDto =
                usuarioStrategy.listarTodos().stream().map(UsuarioMapper::toResponseDto).toList();

        return ok(listaDto);
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
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable int id){
        return ok(UsuarioMapper.toResponseDto(usuarioStrategy.buscarPorId(id)));
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
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioRequestDto usuarioRequestDto){
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequestDto);
        Usuario usuarioSalvo = usuarioStrategy.salvar(usuario);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponseDto(usuarioSalvo);

        return created(null).body(usuarioResponseDto);

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
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid UsuarioRequestDto usuarioRequestDto){
        usuarioStrategy.buscarPorId(id);

        Usuario usuario = UsuarioMapper.toEntity(usuarioRequestDto);
        usuario.setId(id);
        Usuario usuarioSalvo = usuarioStrategy.salvar(usuario);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponseDto(usuarioSalvo);

        return ok(usuarioResponseDto);
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
        usuarioStrategy.buscarPorId(id);
        usuarioStrategy.deletar(id);
        return noContent().build();
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioTokenDto = this.usuarioStrategy.autenticar(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
}
