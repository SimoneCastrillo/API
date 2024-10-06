package buffet.app_web.controllers;

import buffet.app_web.dto.request.avaliacao.AvaliacaoRequestDto;
import buffet.app_web.dto.response.avaliacao.AvaliacaoResponseDto;
import buffet.app_web.entities.Avaliacao;
import buffet.app_web.mapper.AvaliacaoMapper;
import buffet.app_web.strategies.AvaliacaoStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoStrategy avaliacaoStrategy;

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDto>> listarTodos(){
        if (avaliacaoStrategy.listarTodos().isEmpty()){
            return noContent().build();
        }

        List<AvaliacaoResponseDto> listaDto =
                avaliacaoStrategy.listarTodos().stream().map(AvaliacaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> buscarPorId(@PathVariable int id){
        return ok(AvaliacaoMapper.toResponseDto(avaliacaoStrategy.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDto> publicar(@RequestBody @Valid AvaliacaoRequestDto avaliacaoRequestDto){
        Avaliacao avaliacao = avaliacaoStrategy.salvar(AvaliacaoMapper.toEntity(avaliacaoRequestDto));
        AvaliacaoResponseDto avaliacaoResponseDto = AvaliacaoMapper.toResponseDto(avaliacao);
        return ok(avaliacaoResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> atualizar(@RequestBody @Valid AvaliacaoRequestDto avaliacaoRequestDto, @PathVariable int id){
        avaliacaoStrategy.buscarPorId(id);

        Avaliacao avaliacao = AvaliacaoMapper.toEntity(avaliacaoRequestDto);
        avaliacao.setId(id);
        Avaliacao avaliacaoSalva = avaliacaoStrategy.salvar(avaliacao);

        return ok(AvaliacaoMapper.toResponseDto(avaliacaoSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        avaliacaoStrategy.buscarPorId(id);

        avaliacaoStrategy.deletar(id);
        return noContent().build();
    }
}
