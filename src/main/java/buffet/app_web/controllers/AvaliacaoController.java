package buffet.app_web.controllers;

import buffet.app_web.dto.request.avaliacao.AvaliacaoRequestDto;
import buffet.app_web.dto.response.avaliacao.AvaliacaoResponseDto;
import buffet.app_web.entities.Avaliacao;
import buffet.app_web.mapper.AvaliacaoMapper;
import buffet.app_web.strategies.AvaliacaoStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvaliacaoResponseDto> publicar(@ModelAttribute @Valid AvaliacaoRequestDto avaliacaoRequestDto){
        Avaliacao avaliacao = avaliacaoStrategy.salvar(AvaliacaoMapper.toEntity(avaliacaoRequestDto), avaliacaoRequestDto.getTipoEventoId());
        AvaliacaoResponseDto avaliacaoResponseDto = AvaliacaoMapper.toResponseDto(avaliacao);
        return ok(avaliacaoResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> atualizar(@RequestBody @Valid AvaliacaoRequestDto avaliacaoRequestDto, @PathVariable int id){
        avaliacaoStrategy.buscarPorId(id);

        Avaliacao avaliacao = AvaliacaoMapper.toEntity(avaliacaoRequestDto);
        avaliacao.setId(id);
        Avaliacao avaliacaoSalva = avaliacaoStrategy.salvar(avaliacao, avaliacaoRequestDto.getTipoEventoId());

        return ok(AvaliacaoMapper.toResponseDto(avaliacaoSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        avaliacaoStrategy.buscarPorId(id);

        avaliacaoStrategy.deletar(id);
        return noContent().build();
    }

    @GetMapping("/ultimos")
    public ResponseEntity<List<AvaliacaoResponseDto>> listarUltimos5(){
        if (avaliacaoStrategy.listarUltimos5().isEmpty()){
            return noContent().build();
        }

        List<AvaliacaoResponseDto> listaDto =
                avaliacaoStrategy.listarUltimos5().stream().map(AvaliacaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

    @GetMapping("/tipo-de-evento")
    public ResponseEntity<List<AvaliacaoResponseDto>> listarPorTipoDeEvento(@RequestParam String nome){
        if (avaliacaoStrategy.listarPorTipoDeEvento(nome).isEmpty()){
            return noContent().build();
        }

        List<AvaliacaoResponseDto> listaDto =
                avaliacaoStrategy.listarPorTipoDeEvento(nome).stream().map(AvaliacaoMapper::toResponseDto).toList();
        return ok(listaDto);
    }

}
