package buffet.app_web.controllers;

import buffet.app_web.entities.Decoracao;
import buffet.app_web.strategies.DecoracaoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/decoracoes")
public class DecoracaoController {
    @Autowired
    private DecoracaoStrategy decoracaoStrategy;

    @GetMapping
    public ResponseEntity<List<Decoracao>> listarTodos(){
        if (decoracaoStrategy.listarTodos().isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(decoracaoStrategy.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Decoracao> buscarPorId(@PathVariable int id){
        Optional<Decoracao> decoracaoOptional = decoracaoStrategy.buscarPorId(id);

        if (decoracaoOptional.isPresent()){
            return ResponseEntity.status(200).body(decoracaoOptional.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<Decoracao> publicar(@RequestBody Decoracao decoracao){
        decoracao.setId(null);
        return ResponseEntity.status(201).body(decoracaoStrategy.salvar(decoracao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Decoracao> atualizar(@RequestBody Decoracao decoracao, @PathVariable int id){
        if (decoracaoStrategy.buscarPorId(id).isPresent()){
            decoracao.setId(id);
            decoracaoStrategy.salvar(decoracao);

            return ResponseEntity.status(200).body(decoracao);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if (decoracaoStrategy.buscarPorId(id).isPresent()){
            decoracaoStrategy.deletar(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

}
