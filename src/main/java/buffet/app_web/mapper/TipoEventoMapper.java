package buffet.app_web.mapper;

import buffet.app_web.dto.request.tipoevento.TipoEventoRequestDto;
import buffet.app_web.dto.response.tipoevento.TipoEventoResponseDto;
import buffet.app_web.entities.TipoEvento;

public class TipoEventoMapper {
    public static TipoEventoResponseDto toResponseDto(TipoEvento tipoEvento){
        if (tipoEvento == null) return null;

        return TipoEventoResponseDto
                .builder()
                .id(tipoEvento.getId())
                .nome(tipoEvento.getNome())
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
