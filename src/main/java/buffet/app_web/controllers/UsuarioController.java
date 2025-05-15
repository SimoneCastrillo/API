package buffet.app_web.controllers;

import buffet.app_web.dto.request.usuario.*;
import buffet.app_web.dto.response.usuario.UsuarioPorIdResponseDto;
import buffet.app_web.dto.response.usuario.UsuarioResponseDto;
import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.Usuario;
import buffet.app_web.enums.UserRole;
import buffet.app_web.mapper.UsuarioMapper;
import buffet.app_web.repositories.UsuarioRepository;
import buffet.app_web.service.OrcamentoService;
import buffet.app_web.service.UsuarioService;
import buffet.app_web.service.autenticacao.dto.UsuarioLoginDto;
import buffet.app_web.service.autenticacao.dto.UsuarioTokenDto;
import buffet.app_web.strategies.BuffetStrategy;
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
import org.springframework.http.MediaType;
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
    @Autowired
    private BuffetStrategy buffetStrategy;
    @Autowired
    private OrcamentoService orcamentoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public ResponseEntity<UsuarioPorIdResponseDto> buscarPorId(@PathVariable int id){
        Usuario usuario = usuarioStrategy.buscarPorId(id);
        UsuarioPorIdResponseDto usuarioResponseDto = UsuarioMapper.toResponsePorIdDto(usuario);
        usuarioResponseDto.setQtdOrcamento(orcamentoService.countByUsuarioId(id));
        return ok(usuarioResponseDto);
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
    @PostMapping("/{buffetId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto, @PathVariable Long buffetId){
        Usuario usuario = UsuarioMapper.toEntity(usuarioCriacaoDto);
        Usuario usuarioSalvo = usuarioStrategy.salvar(usuario, buffetId);
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

    @PatchMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable int id, @ModelAttribute @Valid UsuarioUpdateDto usuarioUpdateDto){
        Usuario usuarioBusca = usuarioStrategy.buscarPorId(id);
        String senha = usuarioStrategy.buscarPorId(id).getSenha();
        UserRole userRole = usuarioStrategy.buscarPorId(id).getRole();
        Usuario usuario = UsuarioMapper.toEntity(usuarioUpdateDto);
        usuario.setId(id);
        usuario.setSenha(senha);
        usuario.setRole(userRole);
        usuario.setBuffet(usuarioBusca.getBuffet());
        Usuario usuarioSalvo = usuarioStrategy.atualizar(usuario);
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

    @Operation(summary = "Logar usuário", description = """
            # Logar Usuário
            ---
            Autentica o usuário e retorna um token de acesso.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Login inválido",
                    content = @Content()
            )
    })
    @PostMapping("/login/{buffetId}")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto, @PathVariable Long buffetId) {
        UsuarioTokenDto usuarioTokenDto = this.usuarioStrategy.autenticar(usuarioLoginDto, buffetId);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @PatchMapping("/{id}/alterar-senha")
    public ResponseEntity<UsuarioResponseDto> alterarSenha(@RequestBody @Valid MudancaSenhaDto dto, @PathVariable int id){
        Usuario usuario = usuarioStrategy.alterarSenha(dto.getEmail(), dto.getNovaSenha(), dto.getConfirmacaoNovaSenha());
        UsuarioResponseDto responseDto = UsuarioMapper.toResponseDto(usuario);

        return ok(responseDto);
    }

    @PostMapping("/enviar-codigo")
    public ResponseEntity<Void> enviarCodigo(@RequestBody @Valid ConfirmarEmailDto dto){
        usuarioStrategy.enviarCodigo(dto.getEmail());

        return ok().build();
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<Void> verificarCodigo(@RequestBody @Valid CodigoVerficacaoDto dto){
        usuarioStrategy.validar(dto.getEmail(), dto.getCodigo());

        return ok().build();
    }
}
