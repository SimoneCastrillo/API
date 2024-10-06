package buffet.app_web.controllers;

import buffet.app_web.dto.request.tipoevento.TipoEventoRequestDto;
import buffet.app_web.dto.response.tipoevento.TipoEventoResponseDto;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.mapper.TipoEventoMapper;
import buffet.app_web.strategies.TipoEventoStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/tipos-evento")
public class TipoEventoController {
    @Autowired
    private TipoEventoStrategy tipoEventoStrategy;

    @GetMapping
    public ResponseEntity<List<TipoEventoResponseDto>> listarTodos(){
        if (tipoEventoStrategy.listarTodos().isEmpty()){
            return noContent().build();
        }

        List<TipoEventoResponseDto> listDto =
                tipoEventoStrategy.listarTodos().stream().map(TipoEventoMapper::toResponseDto).toList();

        return ok(listDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEventoResponseDto> buscarPorId(@PathVariable int id){
        TipoEventoResponseDto dto = TipoEventoMapper.toResponseDto(tipoEventoStrategy.buscarPorId(id));
        return ok(dto);
    }

    @PostMapping
    public ResponseEntity<TipoEventoResponseDto> criar(@RequestBody @Valid TipoEventoRequestDto tipoEventoRequestDto){
        TipoEvento tipoEventoSalvo = tipoEventoStrategy.salvar(TipoEventoMapper.toEntity(tipoEventoRequestDto));
        TipoEventoResponseDto tipoEventoResponseDto = TipoEventoMapper.toResponseDto(tipoEventoSalvo);

        return created(null).body(tipoEventoResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEventoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid TipoEventoRequestDto tipoEventoRequestDto){
        tipoEventoStrategy.buscarPorId(id);

        TipoEvento tipoEvento = TipoEventoMapper.toEntity(tipoEventoRequestDto);
        tipoEvento.setId(id);
        TipoEvento tipoEventoSalvo = tipoEventoStrategy.salvar(tipoEvento);
        TipoEventoResponseDto tipoEventoResponseDto = TipoEventoMapper.toResponseDto(tipoEventoSalvo);

        return ok(tipoEventoResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        tipoEventoStrategy.buscarPorId(id);

        tipoEventoStrategy.deletar(id);
        return noContent().build();
    }
}
