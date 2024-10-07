package buffet.app_web.controllers;

import buffet.app_web.entities.Decoracao;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/decoracoes")
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
    public ResponseEntity<List<Decoracao>> listarTodos(){
        if (decoracaoStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(decoracaoStrategy.listarTodos());
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
    public ResponseEntity<Decoracao> buscarPorId(@PathVariable int id){
        Optional<Decoracao> decoracaoOptional = decoracaoStrategy.buscarPorId(id);

        if (decoracaoOptional.isPresent()){
            return ResponseEntity.status(200).body(decoracaoOptional.get());
        }

        return ResponseEntity.status(404).build();
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
    @PostMapping
    public ResponseEntity<Decoracao> publicar(@RequestBody @Valid Decoracao decoracao){
        decoracao.setId(null);
        return ResponseEntity.status(201).body(decoracaoStrategy.salvar(decoracao));
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
    public ResponseEntity<Decoracao> atualizar(@RequestBody @Valid Decoracao decoracao, @PathVariable int id){
        if (decoracaoStrategy.buscarPorId(id).isPresent()){
            decoracao.setId(id);
            decoracaoStrategy.salvar(decoracao);

            return ResponseEntity.status(200).body(decoracao);
        }

        return ResponseEntity.status(404).build();
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
        if (decoracaoStrategy.buscarPorId(id).isPresent()){
            decoracaoStrategy.deletar(id);

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

}
