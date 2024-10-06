package buffet.app_web.controllers;

import buffet.app_web.dto.request.decoracao.DecoracaoRequestDto;
import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.entities.Decoracao;
import buffet.app_web.mapper.DecoracaoMapper;
import buffet.app_web.strategies.DecoracaoStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/decoracoes")
public class DecoracaoController {
    @Autowired
    private DecoracaoStrategy decoracaoStrategy;

    @GetMapping
    public ResponseEntity<List<DecoracaoResponseDto>> listarTodos(){
        if (decoracaoStrategy.listarTodos().isEmpty()){
            return noContent().build();
        }

        List<DecoracaoResponseDto> listaDto =
                decoracaoStrategy.listarTodos().stream().map(DecoracaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DecoracaoResponseDto> buscarPorId(@PathVariable int id){
        return ok(DecoracaoMapper.toResponseDto(decoracaoStrategy.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<DecoracaoResponseDto> publicar(@RequestBody @Valid DecoracaoRequestDto decoracaoRequestDto){
        Decoracao decoracao = decoracaoStrategy.salvar(DecoracaoMapper.toEntity(decoracaoRequestDto));
        DecoracaoResponseDto decoracaoResponseDto = DecoracaoMapper.toResponseDto(decoracao);
        return ok(decoracaoResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DecoracaoResponseDto> atualizar(@RequestBody @Valid DecoracaoRequestDto decoracaoRequestDto, @PathVariable int id){
        decoracaoStrategy.buscarPorId(id);

        Decoracao decoracao = DecoracaoMapper.toEntity(decoracaoRequestDto);
        decoracao.setId(id);
        Decoracao decoracaoSalva = decoracaoStrategy.salvar(decoracao);

        return ok(DecoracaoMapper.toResponseDto(decoracaoSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        decoracaoStrategy.buscarPorId(id);

        decoracaoStrategy.deletar(id);
        return noContent().build();
    }

}
