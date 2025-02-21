package buffet.app_web.mapper;

import buffet.app_web.enums.UserRole;
import buffet.app_web.dto.request.usuario.UsuarioCriacaoDto;
import buffet.app_web.dto.request.usuario.UsuarioUpdateDto;
import buffet.app_web.dto.response.usuario.UsuarioPorIdResponseDto;
import buffet.app_web.dto.response.usuario.UsuarioResponseDto;
import buffet.app_web.entities.Usuario;
import buffet.app_web.service.autenticacao.dto.UsuarioTokenDto;

import java.util.Base64;


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
                .role(usuario.getRole())
                .foto(usuario.getFoto())
                .build();
    }

    public static UsuarioPorIdResponseDto toResponsePorIdDto(Usuario usuario){
        if (usuario == null) return null;

        return UsuarioPorIdResponseDto
                .builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .telefone(usuario.getTelefone())
                .role(usuario.getRole())
                .foto(usuario.getFoto())
                .build();
    }

    public static Usuario toEntity(UsuarioUpdateDto dto){
        if (dto == null) return null;

        String base64Image = null;
        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            try {

                base64Image = Base64.getEncoder().encodeToString(dto.getFoto().getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter a imagem para Base64", e);
            }
        }

        return Usuario
                .builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .foto(base64Image)
                .build();
    }

    public static Usuario toEntity(UsuarioCriacaoDto dto){
        if (dto == null) return null;


        return Usuario
                .builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .role(UserRole.USUARIO)
                .telefone(dto.getTelefone())
                .build();
    }

    public static UsuarioTokenDto of(Usuario usuario, String token, Integer qtdOrcamento){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setId(usuario.getId());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setTelefone(usuario.getTelefone());
        usuarioTokenDto.setQtdOrcamento(qtdOrcamento);
        usuarioTokenDto.setRole(usuario.getRole());
        usuarioTokenDto.setToken(token);
        usuarioTokenDto.setFoto(usuario.getFoto());

        return usuarioTokenDto;
    }

}
