package buffet.app_web.controllers;

import buffet.app_web.dto.request.endereco.EnderecoRequestDto;
import buffet.app_web.dto.response.endereco.EnderecoResponseDto;
import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.Endereco;
import buffet.app_web.mapper.EnderecoMapper;
import buffet.app_web.service.BuffetService;
import buffet.app_web.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private BuffetService buffetService;

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDto>> listar() {
        List<Endereco> enderecos = enderecoService.listar();

        if (enderecos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EnderecoResponseDto> dtos = enderecos.stream()
                .map(EnderecoMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> buscarPorId(@PathVariable Long id) {
        Endereco endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(EnderecoMapper.toResponseDto(endereco));
    }

    @PostMapping
    public ResponseEntity<EnderecoResponseDto> salvar(@RequestBody @Valid EnderecoRequestDto dto) {
        Endereco endereco = EnderecoMapper.toEntity(dto);
        Endereco salvo = enderecoService.salvar(endereco, dto.getBuffetId());
        return ResponseEntity.status(HttpStatus.CREATED).body(EnderecoMapper.toResponseDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> atualizar(@PathVariable Long id,
                                                         @RequestBody @Valid EnderecoRequestDto dto) {
        Buffet buffet = buffetService.buscarPorId(dto.getBuffetId());
        Endereco endereco = EnderecoMapper.toEntity(dto);
        Endereco atualizado = enderecoService.atualizar(endereco, id);
        return ResponseEntity.ok(EnderecoMapper.toResponseDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
