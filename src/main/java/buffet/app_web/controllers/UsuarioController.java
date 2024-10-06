package buffet.app_web.controllers;

import buffet.app_web.dto.request.usuario.UsuarioRequestDto;
import buffet.app_web.dto.response.usuario.UsuarioResponseDto;
import buffet.app_web.entities.Usuario;
import buffet.app_web.mapper.UsuarioMapper;
import buffet.app_web.strategies.UsuarioStrategy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioStrategy usuarioStrategy;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarTodos(){
        if (usuarioStrategy.listarTodos().isEmpty()){
            return status(204).build();
        }

        List<UsuarioResponseDto> listaDto =
                usuarioStrategy.listarTodos().stream().map(UsuarioMapper::toResponseDto).toList();

        return ok(listaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable int id){
        return ok(UsuarioMapper.toResponseDto(usuarioStrategy.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioRequestDto usuarioRequestDto){
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequestDto);
        Usuario usuarioSalvo = usuarioStrategy.salvar(usuario);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponseDto(usuarioSalvo);

        return created(null).body(usuarioResponseDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid UsuarioRequestDto usuarioRequestDto){
        usuarioStrategy.buscarPorId(id);

        Usuario usuario = UsuarioMapper.toEntity(usuarioRequestDto);
        usuario.setId(id);
        Usuario usuarioSalvo = usuarioStrategy.salvar(usuario);
        UsuarioResponseDto usuarioResponseDto = UsuarioMapper.toResponseDto(usuarioSalvo);

        return ok(usuarioResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        usuarioStrategy.buscarPorId(id);
        usuarioStrategy.deletar(id);
        return noContent().build();
    }
}
