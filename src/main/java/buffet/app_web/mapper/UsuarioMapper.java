package buffet.app_web.mapper;

import buffet.app_web.dto.request.usuario.UsuarioRequestDto;
import buffet.app_web.dto.response.usuario.UsuarioResponseDto;
import buffet.app_web.entities.Usuario;
import buffet.app_web.service.autenticacao.dto.UsuarioTokenDto;


public class UsuarioMapper {
    public static UsuarioResponseDto toResponseDto(Usuario usuario){
        if (usuario == null) return null;

        return UsuarioResponseDto
                .builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .telefone(usuario.getTelefone())
                .build();
    }

    public static Usuario toEntity(UsuarioRequestDto dto){
        if (dto == null) return null;

        return Usuario
                .builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .telefone(dto.getTelefone())
                .build();
    }

    public static UsuarioTokenDto of(Usuario usuario, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

}
