package buffet.app_web.controllers;

import buffet.app_web.dto.request.avaliacao.AvaliacaoRequestDto;
import buffet.app_web.dto.response.avaliacao.AvaliacaoResponseDto;
import buffet.app_web.entities.Avaliacao;
import buffet.app_web.mapper.AvaliacaoMapper;
import buffet.app_web.strategies.AvaliacaoStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoStrategy avaliacaoStrategy;

    @Operation(summary = "Listar Todas as Avaliações", description = """
        # Listar Avaliações
        ---
        Retorna uma lista de todas as avaliações disponíveis.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações listadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AvaliacaoResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDto>> listarTodos(){
        if (avaliacaoStrategy.listarTodos().isEmpty()){
            return noContent().build();
        }

        List<AvaliacaoResponseDto> listaDto =
                avaliacaoStrategy.listarTodos().stream().map(AvaliacaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }
    @Operation(summary = "Buscar Avaliação por ID", description = """
        # Buscar Avaliação
        ---
        Retorna uma avaliação específica com base no ID fornecido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AvaliacaoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada",
                    content = @Content()
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> buscarPorId(@PathVariable int id){
        return ok(AvaliacaoMapper.toResponseDto(avaliacaoStrategy.buscarPorId(id)));
    }

    @Operation(summary = "Publicar Avaliação", description = """
        # Publicar Avaliação
        ---
        Salva uma nova avaliação com os dados fornecidos.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação publicada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AvaliacaoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de avaliação inválidos",
                    content = @Content()
            ),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvaliacaoResponseDto> publicar(@ModelAttribute @Valid AvaliacaoRequestDto avaliacaoRequestDto){
        Avaliacao avaliacao = avaliacaoStrategy.salvar(AvaliacaoMapper.toEntity(avaliacaoRequestDto), avaliacaoRequestDto.getTipoEventoId());
        AvaliacaoResponseDto avaliacaoResponseDto = AvaliacaoMapper.toResponseDto(avaliacao);
        return ok(avaliacaoResponseDto);
    }

    @Operation(summary = "Atualizar Avaliação", description = """
        # Atualizar Avaliação
        ---
        Atualiza uma avaliação existente com os dados fornecidos.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AvaliacaoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de avaliação inválidos",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada",
                    content = @Content()
            ),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvaliacaoResponseDto> atualizar(@ModelAttribute @Valid AvaliacaoRequestDto avaliacaoRequestDto, @PathVariable int id){
        avaliacaoStrategy.buscarPorId(id);

        Avaliacao avaliacao = AvaliacaoMapper.toEntity(avaliacaoRequestDto);
        avaliacao.setId(id);
        Avaliacao avaliacaoSalva = avaliacaoStrategy.salvar(avaliacao, avaliacaoRequestDto.getTipoEventoId());

        return ok(AvaliacaoMapper.toResponseDto(avaliacaoSalva));
    }

    @Operation(summary = "Deletar Avaliação", description = """
        # Deletar Avaliação
        ---
        Remove uma avaliação existente com base no ID fornecido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada",
                    content = @Content()
            ),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        avaliacaoStrategy.buscarPorId(id);

        avaliacaoStrategy.deletar(id);
        return noContent().build();
    }

    @Operation(summary = "Listar Últimas 5 Avaliações", description = """
        # Listar Últimas Avaliações
        ---
        Retorna as últimas 5 avaliações registradas.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Últimas 5 avaliações listadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AvaliacaoResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada",
                    content = @Content()
            )
    })
    @GetMapping("/ultimos")
    public ResponseEntity<List<AvaliacaoResponseDto>> listarUltimos5(){
        if (avaliacaoStrategy.listarUltimos5().isEmpty()){
            return noContent().build();
        }

        List<AvaliacaoResponseDto> listaDto =
                avaliacaoStrategy.listarUltimos5().stream().map(AvaliacaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

    @Operation(summary = "Listar Avaliações por Tipo de Evento", description = """
        # Listar Avaliações
        ---
        Retorna uma lista de avaliações filtradas pelo tipo de evento especificado.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações listadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AvaliacaoResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada para o tipo de evento especificado",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content()
            )
    })
    @GetMapping("/tipo-de-evento")
    public ResponseEntity<List<AvaliacaoResponseDto>> listarPorTipoDeEvento(@RequestParam String nome){
        if (avaliacaoStrategy.listarPorTipoDeEvento(nome).isEmpty()){
            return noContent().build();
        }

        List<AvaliacaoResponseDto> listaDto =
                avaliacaoStrategy.listarPorTipoDeEvento(nome).stream().map(AvaliacaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

}
