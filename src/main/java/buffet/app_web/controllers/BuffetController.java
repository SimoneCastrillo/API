package buffet.app_web.controllers;

import buffet.app_web.dto.request.buffet.BuffetRequestDto;
import buffet.app_web.dto.response.buffet.BuffetResponseDto;
import buffet.app_web.entities.Buffet;
import buffet.app_web.mapper.BuffetMapper;
import buffet.app_web.service.BuffetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buffets")
public class BuffetController {
    @Autowired
    private BuffetService buffetService;

    @GetMapping
    public ResponseEntity<List<BuffetResponseDto>> listar() {
        List<BuffetResponseDto> buffets =
                buffetService.listar().stream().map(BuffetMapper::toResponseDto).toList();
        return ResponseEntity.ok(buffets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuffetResponseDto> buscarPorId(@PathVariable Long id) {
        BuffetResponseDto buffet = BuffetMapper.toResponseDto(buffetService.buscarPorId(id));
        return ResponseEntity.ok(buffet);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BuffetResponseDto> salvar(@ModelAttribute @Valid BuffetRequestDto buffetRequestDto) {
        Buffet novoBuffet = buffetService.salvar(BuffetMapper.toEntity(buffetRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(BuffetMapper.toResponseDto(novoBuffet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuffetResponseDto> atualizar(@RequestBody BuffetRequestDto buffetRequestDto, @PathVariable Long id) {
        Buffet buffet = BuffetMapper.toEntity(buffetRequestDto);
        Buffet buffetAtualizado = buffetService.atualizar(buffet, id);
        BuffetResponseDto response = BuffetMapper.toResponseDto(buffetAtualizado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        buffetService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
