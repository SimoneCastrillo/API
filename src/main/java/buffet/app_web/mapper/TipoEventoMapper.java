package buffet.app_web.mapper;

import buffet.app_web.dto.request.tipoevento.TipoEventoRequestDto;
import buffet.app_web.dto.response.tipoevento.TipoEventoResponseDto;
import buffet.app_web.dto.response.tipoevento.TipoEventoResponseDto;
import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.TipoEvento;

public class TipoEventoMapper {
    public static TipoEventoResponseDto toResponseDto(TipoEvento tipoEvento){
        if (tipoEvento == null) return null;

        Buffet buffet = tipoEvento.getBuffet();

        TipoEventoResponseDto.BuffetResponseDto buffetDto = TipoEventoResponseDto.BuffetResponseDto
                .builder()
                .id(buffet.getId())
                .nome(buffet.getNome())
                .email(buffet.getEmail())
                .descricao(buffet.getDescricao())
                .imagem(buffet.getImagem())
                .urlSite(buffet.getUrlSite())
                .telefone(buffet.getTelefone())
                .plano(buffet.getPlano())
                .build();

        return TipoEventoResponseDto
                .builder()
                .id(tipoEvento.getId())
                .nome(tipoEvento.getNome())
                .buffet(buffetDto)
                .build();
    }

    public static TipoEvento toEntity(TipoEventoRequestDto dto){
        if (dto == null) return null;

        return TipoEvento
                .builder()
                .nome(dto.getNome())
                .build();
    }
}
