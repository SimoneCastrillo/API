package buffet.app_web.controllers;

import buffet.app_web.dto.request.orcamento.OrcamentoRequestDto;
import buffet.app_web.dto.response.orcamento.OrcamentoResponseDto;
import buffet.app_web.entities.Orcamento;
import buffet.app_web.mapper.OrcamentoMapper;
import buffet.app_web.strategies.OrcamentoStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {
    @Autowired
    private OrcamentoStrategy orcamentoStrategy;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDto> buscarPorId(@PathVariable int id){
        return status(200).body(OrcamentoMapper.toResponseDto(orcamentoStrategy.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<OrcamentoResponseDto> criar(@RequestBody @Valid OrcamentoRequestDto orcamentoRequestDto){
        Orcamento orcamento = orcamentoStrategy.salvar(OrcamentoMapper.toEntity(orcamentoRequestDto));
        OrcamentoResponseDto responseDto = OrcamentoMapper.toResponseDto(orcamento);
        return created(null).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDto> atualizar(@RequestBody @Valid OrcamentoRequestDto orcamentoRequestDto, @PathVariable int id){
        orcamentoStrategy.buscarPorId(id);

        Orcamento orcamento = OrcamentoMapper.toEntity(orcamentoRequestDto);
        orcamento.setId(id);

        Orcamento orcamentoSalvo = orcamentoStrategy.salvar(orcamento);

        OrcamentoResponseDto responseDto = OrcamentoMapper.toResponseDto(orcamentoSalvo);

        return ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        orcamentoStrategy.buscarPorId(id);

        orcamentoStrategy.deletar(id);
        return noContent().build();
    }

    @PatchMapping("/{id}/cancelamento")
    public ResponseEntity<Orcamento> cancelarEvento(@PathVariable int id){
        orcamentoStrategy.cancelarEvento(id);
        return noContent().build();
    }
}
