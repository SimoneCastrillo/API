package buffet.app_web.controllers;

import buffet.app_web.entities.TipoEvento;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-evento")
@Tag(name = "Tipos de Evento", description = "Operações relacionadas a tipos de eventos")
public class TipoEventoController {
    @Autowired
    private TipoEventoStrategy itemCardapioStrategy;

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
    public ResponseEntity<List<TipoEvento>> listarTodos(){
        if (itemCardapioStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(itemCardapioStrategy.listarTodos());
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
    public ResponseEntity<TipoEvento> buscarPorId(@PathVariable int id){
        Optional<TipoEvento> itemOpt = itemCardapioStrategy.buscarPorId(id);
        if (itemOpt.isPresent()){
            return ResponseEntity.status(200).body(itemOpt.get());
        }

        return ResponseEntity.status(404).build();
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
    @PostMapping
    public ResponseEntity<TipoEvento> criar(@RequestBody @Valid TipoEvento item){
        item.setId(null);
        return ResponseEntity.status(201).body(itemCardapioStrategy.salvar(item));
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
    @PutMapping("/{id}")
    public ResponseEntity<TipoEvento> atualizar(@PathVariable int id, @RequestBody @Valid TipoEvento itemAtualizado){
        if (itemCardapioStrategy.buscarPorId(id).isPresent()){
            itemAtualizado.setId(id);
            itemCardapioStrategy.salvar(itemAtualizado);

            return ResponseEntity.status(200).body(itemAtualizado);
        }

        return ResponseEntity.status(404).build();
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if (itemCardapioStrategy.buscarPorId(id).isPresent()){
            itemCardapioStrategy.deletar(id);

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }
}
