package buffet.app_web.controllers;

import buffet.app_web.entities.Orcamento;
import buffet.app_web.strategies.OrcamentoStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orcamentos")
@Tag(name = "Orcamentos",  description = "Operações relacionadas a orçamentos")
public class OrcamentoController {
    @Autowired
    private OrcamentoStrategy orcamentoStrategy;

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
    public ResponseEntity<List<Orcamento>> listarTodos(){
        if (orcamentoStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(orcamentoStrategy.listarTodos());
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
    public ResponseEntity<Orcamento> buscarPorId(@PathVariable int id){
        Optional<Orcamento> orcamentoOptional = orcamentoStrategy.buscarPorId(id);
        if (orcamentoOptional.isPresent()){
            return ResponseEntity.status(200).body(orcamentoOptional.get());
        }

        return ResponseEntity.status(404).build();
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
    public ResponseEntity<Orcamento> criar(@RequestBody @Valid Orcamento orcamento){
        orcamento.setId(null);
        return ResponseEntity.status(201).body(orcamentoStrategy.salvar(orcamento));
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
    public ResponseEntity<Orcamento> atualizar(@RequestBody @Valid Orcamento orcamento, @PathVariable int id){
        if (orcamentoStrategy.buscarPorId(id).isPresent()){
            orcamento.setId(null);
            return ResponseEntity.status(200).body(orcamentoStrategy.salvar(orcamento));
        }

        return ResponseEntity.status(404).build();
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
        if (orcamentoStrategy.buscarPorId(id).isPresent()){
            orcamentoStrategy.deletar(id);

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }
}
