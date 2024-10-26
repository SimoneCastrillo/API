package buffet.app_web.controllers;

import buffet.app_web.dto.request.orcamento.OrcamentoRequestDto;
import buffet.app_web.dto.response.orcamento.OrcamentoResponseDto;
import buffet.app_web.entities.Orcamento;
import buffet.app_web.mapper.OrcamentoMapper;
import buffet.app_web.service.ExportacaoService;
import buffet.app_web.service.GoogleService;
import buffet.app_web.strategies.OrcamentoStrategy;
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

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/orcamentos")
@Tag(name = "Orcamentos",  description = "Operações relacionadas a orçamentos")
public class OrcamentoController {
    @Autowired
    private OrcamentoStrategy orcamentoStrategy;

    @Autowired
    private ExportacaoService exportacaoService;

    @Autowired
    private GoogleService googleService;


    @Operation(summary = "Listar todos os orçamentos", description = """
            # Listar todos os orçamentos
            ---
            O endpoint lista todos os orçamentos cadastrados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista todos os orçamentos",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Orcamento.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Quando não tem orçamentos cadastrados",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDto>> listarTodos(){
        if (orcamentoStrategy.listarTodos().isEmpty()){
            return noContent().build();
        }

        List<OrcamentoResponseDto> listaDto =
                orcamentoStrategy.listarTodos()
                        .stream()
                        .map(OrcamentoMapper::toResponseDto)
                        .toList();

        return ok(listaDto);
    }

    @Operation(summary = "Buscar um orçamento por ID", description = """
            # Buscar orçamento
            ---
            Retorna o orçamento correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orçamento encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Orcamento.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado",
                    content = @Content()
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDto> buscarPorId(@PathVariable int id){
        return status(200).body(OrcamentoMapper.toResponseDto(orcamentoStrategy.buscarPorId(id)));
    }

    @Operation(summary = "Criar um novo orçamento", description = """
            # Criar orçamento
            ---
            Cria e retorna o novo orçamento.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orçamento criado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Orcamento.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<OrcamentoResponseDto> criar(
            @RequestBody @Valid OrcamentoRequestDto orcamentoRequestDto
    ){

        Integer tipoEventoId = orcamentoRequestDto.getTipoEventoId();
        Integer usuarioId = orcamentoRequestDto.getUsuarioId();
        Integer decoracaoId = orcamentoRequestDto.getDecoracaoId();

        Orcamento orcamento = orcamentoStrategy.salvar(
                OrcamentoMapper.toEntity(orcamentoRequestDto), tipoEventoId, usuarioId, decoracaoId);
        OrcamentoResponseDto responseDto = OrcamentoMapper.toResponseDto(orcamento);

        googleService.criarEvento(orcamento);
        return created(null).body(responseDto);
    }

    @Operation(summary = "Atualizar um orçamento", description = """
            # Atualizar orçamento
            ---
            Atualiza o orçamento correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orçamento atualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Orcamento.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado",
                    content = @Content()
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDto> atualizar(
            @RequestBody @Valid OrcamentoRequestDto orcamentoRequestDto, @PathVariable int id,
            Integer tipoEventoId,
            Integer usuarioId,
            Integer decoracaoId
    ){

        orcamentoStrategy.buscarPorId(id);

        Orcamento orcamento = OrcamentoMapper.toEntity(orcamentoRequestDto);
        orcamento.setId(id);

        Orcamento orcamentoSalvo = orcamentoStrategy.salvar(orcamento, tipoEventoId, usuarioId, decoracaoId);

        OrcamentoResponseDto responseDto = OrcamentoMapper.toResponseDto(orcamentoSalvo);

        return ok(responseDto);
    }

    @Operation(summary = "Deletar um orçamento", description = """
            # Deletar orçamento
            ---
            Deleta o orçamento correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Orçamento deletado",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado",
                    content = @Content()
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        orcamentoStrategy.buscarPorId(id);

        orcamentoStrategy.deletar(id);
        return noContent().build();
    }

    @Operation(summary = "Cancelar Evento", description = """
        # Cancelar Evento
        ---
        Cancela um evento baseado no ID fornecido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento cancelado com sucesso",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content()
            )
    })
    @PatchMapping("/{id}/cancelamento")
    public ResponseEntity<Orcamento> cancelarEvento(@PathVariable int id){
        orcamentoStrategy.cancelarEvento(id);
        return noContent().build();
    }
}
