package buffet.app_web.mapper;

import buffet.app_web.dto.request.buffet.BuffetRequestDto;
import buffet.app_web.dto.response.buffet.BuffetResponseDto;
import buffet.app_web.entities.Buffet;

import java.util.Base64;

public class BuffetMapper {
    public static BuffetResponseDto toResponseDto(Buffet buffet){
        if (buffet == null) return null;

        return BuffetResponseDto
                .builder()
                .id(buffet.getId())
                .imagem(buffet.getImagem())
                .nome(buffet.getNome())
                .email(buffet.getEmail())
                .urlSite(buffet.getUrlSite())
                .telefone(buffet.getTelefone())
                .plano(buffet.getPlano())
                .build();
    }

    public static Buffet toEntity(BuffetRequestDto dto){
        if (dto == null) return null;

        String base64image = null;

        if (dto.getImagem() != null && !dto.getImagem().isEmpty()) {
            try {
                base64image = Base64.getEncoder().encodeToString(dto.getImagem().getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter a imagem para Base64", e);
            }
        }

        return Buffet
                .builder()
                .imagem(base64image)
                .nome(dto.getNome())
                .email(dto.getEmail())
                .urlSite(dto.getUrlSite())
                .telefone(dto.getTelefone())
                .plano(dto.getPlano())
                .build();
    }
}
