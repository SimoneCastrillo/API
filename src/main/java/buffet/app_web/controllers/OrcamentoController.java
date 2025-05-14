package buffet.app_web.controllers;

import buffet.app_web.dto.request.orcamento.OrcamentoConfirmacaoDto;
import buffet.app_web.dto.request.orcamento.OrcamentoRequestDto;
import buffet.app_web.dto.response.orcamento.OrcamentoResponseDto;
import buffet.app_web.dto.response.usuario.UsuarioResponseDto;
import buffet.app_web.entities.Orcamento;
import buffet.app_web.mapper.OrcamentoMapper;
import buffet.app_web.mapper.UsuarioMapper;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

        exportacaoService.exportarOrcamento();
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
            @RequestBody @Valid OrcamentoRequestDto orcamentoConfirmacaoDto
    ){

        Integer tipoEventoId = orcamentoConfirmacaoDto.getTipoEventoId();
        Integer usuarioId = orcamentoConfirmacaoDto.getUsuarioId();
        Integer decoracaoId = orcamentoConfirmacaoDto.getDecoracaoId();
        Long buffetId = orcamentoConfirmacaoDto.getBuffetId();
        Long enderecoId = orcamentoConfirmacaoDto.getEnderecoId();

        Orcamento orcamento = orcamentoStrategy.salvar(
                OrcamentoMapper.toEntity(orcamentoConfirmacaoDto), tipoEventoId, usuarioId, decoracaoId, buffetId, enderecoId);
        OrcamentoResponseDto responseDto = OrcamentoMapper.toResponseDto(orcamento);

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
            @RequestBody @Valid OrcamentoRequestDto orcamentoConfirmacaoDto, @PathVariable int id, Authentication authentication
    ){

        Integer tipoEventoId = orcamentoConfirmacaoDto.getTipoEventoId();
        Integer usuarioId = orcamentoConfirmacaoDto.getUsuarioId();
        Integer decoracaoId = orcamentoConfirmacaoDto.getDecoracaoId();
        Long buffetId = orcamentoConfirmacaoDto.getBuffetId();
        Long enderecoId = orcamentoConfirmacaoDto.getEnderecoId();

        Orcamento orcamentoBusca = orcamentoStrategy.buscarPorId(id);

        Orcamento orcamento = OrcamentoMapper.toEntity(orcamentoConfirmacaoDto);
        orcamento.setId(id);
        orcamento.setGoogleEventoId(orcamentoBusca.getGoogleEventoId());
        orcamento.setStatus(orcamentoBusca.getStatus());

        Orcamento orcamentoSalvo = orcamentoStrategy.atualizar(orcamento, tipoEventoId, usuarioId, decoracaoId, buffetId, enderecoId, authentication);

        OrcamentoResponseDto responseDto = OrcamentoMapper.toResponseDto(orcamentoSalvo);

        return ok(responseDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/confirmar-dados")
    public ResponseEntity<OrcamentoResponseDto> confirmarDadosDoEvento(
            @RequestBody @Valid OrcamentoConfirmacaoDto orcamentoConfirmacaoDto, @PathVariable int id
    ){

        Integer tipoEventoId = orcamentoConfirmacaoDto.getTipoEventoId();
        Integer decoracaoId = orcamentoConfirmacaoDto.getDecoracaoId();
        Long buffetId = Long.valueOf(orcamentoConfirmacaoDto.getBuffetId());
        Long enderecoId =Long.valueOf(orcamentoConfirmacaoDto.getEnderecoId());

        Orcamento orcamentoBusca = orcamentoStrategy.buscarPorId(id);

        Orcamento orcamento = OrcamentoMapper.toEntity(orcamentoConfirmacaoDto);
        orcamento.setId(id);
        orcamento.setGoogleEventoId(orcamentoBusca.getGoogleEventoId());
        orcamento.setStatus(orcamentoBusca.getStatus());
        orcamento.setCancelado(orcamentoBusca.getCancelado());

        orcamento.setUsuario(orcamentoBusca.getUsuario());

        Orcamento orcamentoSalvo = orcamentoStrategy.confirmarDadosDoEvento(orcamento, tipoEventoId, decoracaoId, buffetId, enderecoId);

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
    public ResponseEntity<Void> cancelarEvento(@PathVariable int id, Authentication authentication){
        orcamentoStrategy.cancelarEvento(id, authentication);
        return noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/desfazer-cancelamento")
    public ResponseEntity<Void> desfazerCancelamento(@PathVariable int id) {
        orcamentoStrategy.desfazerCancelamento(id);
        return noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarEvento(@PathVariable int id){
        orcamentoStrategy.confirmarEvento(id);
        return noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/atualizar-status-expirados")
    public ResponseEntity<Void> atualizarStatusOrcamentosExpirados() {
        orcamentoStrategy.finalizarOrcamentosExpirados();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<OrcamentoResponseDto>> listarPorUsuarioId(@PathVariable int id){
        if (orcamentoStrategy.findByUsuarioId(id).isEmpty()){
            return noContent().build();
        }

        List<OrcamentoResponseDto> orcamentos =
                orcamentoStrategy.findByUsuarioId(id).stream().map(OrcamentoMapper::toResponseDto).toList();

        return ok(orcamentos);
    }


}
