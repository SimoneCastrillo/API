package buffet.app_web.controllers;

import buffet.app_web.entities.Orcamento;
import buffet.app_web.strategies.OrcamentoStrategy;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {
    @Autowired
    private OrcamentoStrategy orcamentoStrategy;

    @GetMapping
    public ResponseEntity<List<Orcamento>> listarTodos(){
        if (orcamentoStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(orcamentoStrategy.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarPorId(@PathVariable int id){
        Optional<Orcamento> orcamentoOptional = orcamentoStrategy.buscarPorId(id);
        if (orcamentoOptional.isPresent()){
            return ResponseEntity.status(200).body(orcamentoOptional.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<Orcamento> criar(@RequestBody @Valid Orcamento orcamento){
        orcamento.setId(null);
        return ResponseEntity.status(201).body(orcamentoStrategy.salvar(orcamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizar(@RequestBody @Valid Orcamento orcamento, @PathVariable int id){
        if (orcamentoStrategy.buscarPorId(id).isPresent()){
            orcamento.setId(id);
            return ResponseEntity.status(200).body(orcamentoStrategy.salvar(orcamento));
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if (orcamentoStrategy.buscarPorId(id).isPresent()){
            orcamentoStrategy.deletar(id);

            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }
}
