package buffet.app_web.controllers;

import buffet.app_web.dto.request.decoracao.DecoracaoRequestDto;
import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.mapper.DecoracaoMapper;
import buffet.app_web.strategies.DecoracaoStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/decoracoes")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Decorações", description = "Operações relacionadas a decorações")
public class DecoracaoController {
    @Autowired
    private DecoracaoStrategy decoracaoStrategy;

    @Operation(summary = "Listar todas as decorações", description = """
            # Listar todas as decorações
            ---
            O endpoint lista todas as decorações cadastradas.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista todas as decorações",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Decoracao.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Quando não tem decorações cadastradas",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<List<DecoracaoResponseDto>> listarTodos(){
        if (decoracaoStrategy.listarTodos().isEmpty()){
            return noContent().build();
        }

        List<DecoracaoResponseDto> listaDto =
                decoracaoStrategy.listarTodos().stream().map(DecoracaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

    @Operation(summary = "Buscar uma decoração por ID", description = """
            # Buscar decoração
            ---
            Retorna a decoração correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decoração encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Decoracao.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Decoração não encontrada",
                    content = @Content()
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DecoracaoResponseDto> buscarPorId(@PathVariable int id){
        return ok(DecoracaoMapper.toResponseDto(decoracaoStrategy.buscarPorId(id)));
    }

    @Operation(summary = "Criar uma nova decoração", description = """
            # Criar decoração
            ---
            Cria e retorna a nova decoração.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Decoração criada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Decoracao.class)
                    )
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DecoracaoResponseDto> publicar(@ModelAttribute @Valid DecoracaoRequestDto decoracaoRequestDto) {
        System.out.println("Recebendo requisição para publicar decoração: {}" + decoracaoRequestDto.getNome());
        Decoracao decoracao = decoracaoStrategy.salvar(DecoracaoMapper.toEntity(decoracaoRequestDto));
        System.out.println("Decoração salva com sucesso: {}" + decoracao.getId());
        DecoracaoResponseDto decoracaoResponseDto = DecoracaoMapper.toResponseDto(decoracao);
        return ResponseEntity.ok(decoracaoResponseDto);
    }

    @Operation(summary = "Atualizar uma decoração", description = """
            # Atualizar decoração
            ---
            Atualiza a decoração correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decoração atualizada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Decoracao.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Decoração não encontrada",
                    content = @Content()
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<DecoracaoResponseDto> atualizar(@RequestBody @Valid DecoracaoRequestDto decoracaoRequestDto, @PathVariable int id){
        decoracaoStrategy.buscarPorId(id);

        Decoracao decoracao = DecoracaoMapper.toEntity(decoracaoRequestDto);
        decoracao.setId(id);
        Decoracao decoracaoSalva = decoracaoStrategy.salvar(decoracao);

        return ok(DecoracaoMapper.toResponseDto(decoracaoSalva));
    }

    @Operation(summary = "Deletar uma decoração", description = """
            # Deletar decoração
            ---
            Deleta a decoração correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Decoração deletada",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Decoração não encontrada",
                    content = @Content()
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        decoracaoStrategy.buscarPorId(id);

        decoracaoStrategy.deletar(id);
        return noContent().build();
    }

    @GetMapping("/tipo-de-evento")
    public ResponseEntity<List<DecoracaoResponseDto>> listarTodos(@RequestParam String nome){
        if (decoracaoStrategy.listarPorTipoDeEvento(nome).isEmpty()){
            return noContent().build();
        }

        List<DecoracaoResponseDto> listaDto =
                decoracaoStrategy.listarPorTipoDeEvento(nome).stream().map(DecoracaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

}
