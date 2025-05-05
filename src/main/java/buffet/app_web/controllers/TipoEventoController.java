package buffet.app_web.controllers;

import buffet.app_web.dto.request.tipoevento.TipoEventoRequestDto;
import buffet.app_web.dto.response.tipoevento.TipoEventoResponseDto;
import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.mapper.TipoEventoMapper;
import buffet.app_web.strategies.TipoEventoStrategy;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/tipos-evento")
@Tag(name = "Tipos de Evento", description = "Operações relacionadas a tipos de eventos")
public class TipoEventoController {
    @Autowired
    private TipoEventoStrategy tipoEventoStrategy;

    @Operation(summary = "Listar todos os tipos de evento", description = """
            # Listar todos os tipos de evento
            ---
            O endpoint lista todos os tipos de evento cadastrados.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista todos os tipos de evento",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TipoEvento.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Quando não tem tipos de evento cadastrados",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<List<TipoEventoResponseDto>> listarTodos(Long buffetId){
        if (tipoEventoStrategy.listarTodos(buffetId).isEmpty()){
            return noContent().build();
        }

        List<TipoEventoResponseDto> listDto =
                tipoEventoStrategy.listarTodos(buffetId).stream().map(TipoEventoMapper::toResponseDto).toList();

        return ok(listDto);
    }

    @Operation(summary = "Buscar um tipo de evento por ID", description = """
            # Buscar tipo de evento
            ---
            Retorna o tipo de evento correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de evento encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoEvento.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Tipo de evento não encontrado",
                    content = @Content()
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoEventoResponseDto> buscarPorId(@PathVariable int id){
        TipoEventoResponseDto dto = TipoEventoMapper.toResponseDto(tipoEventoStrategy.buscarPorId(id));
        return ok(dto);
    }

    @Operation(summary = "Criar um novo tipo de evento", description = """
            # Criar tipo de evento
            ---
            Cria e retorna o novo tipo de evento.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de evento criado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoEvento.class)
                    )
            )
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TipoEventoResponseDto> criar(@RequestBody @Valid TipoEventoRequestDto tipoEventoRequestDto, Long buffetId){
        TipoEvento tipoEventoSalvo = tipoEventoStrategy.salvar(TipoEventoMapper.toEntity(tipoEventoRequestDto), buffetId);
        TipoEventoResponseDto tipoEventoResponseDto = TipoEventoMapper.toResponseDto(tipoEventoSalvo);

        return created(null).body(tipoEventoResponseDto);
    }

    @Operation(summary = "Atualizar um tipo de evento", description = """
            # Atualizar tipo de evento
            ---
            Atualiza o tipo de evento correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de evento atualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoEvento.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Tipo de evento não encontrado",
                    content = @Content()
            )
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TipoEventoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid TipoEventoRequestDto tipoEventoRequestDto, Long buffetId){
        tipoEventoStrategy.buscarPorId(id);

        TipoEvento tipoEvento = TipoEventoMapper.toEntity(tipoEventoRequestDto);
        tipoEvento.setId(id);

        TipoEvento tipoEventoSalvo = tipoEventoStrategy.salvar(tipoEvento, buffetId);
        TipoEventoResponseDto tipoEventoResponseDto = TipoEventoMapper.toResponseDto(tipoEventoSalvo);

        return ok(tipoEventoResponseDto);
    }

    @Operation(summary = "Deletar um tipo de evento", description = """
            # Deletar tipo de evento
            ---
            Deleta o tipo de evento correspondente ao ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de evento deletado",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Tipo de evento não encontrado",
                    content = @Content()
            )
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        tipoEventoStrategy.buscarPorId(id);

        tipoEventoStrategy.deletar(id);
        return noContent().build();
    }
}
