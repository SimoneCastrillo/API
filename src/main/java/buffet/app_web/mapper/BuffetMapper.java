package buffet.app_web.mapper;

import buffet.app_web.dto.request.buffet.BuffetRequestDto;
import buffet.app_web.dto.response.buffet.BuffetResponseDto;
import buffet.app_web.entities.Buffet;

public class BuffetMapper {
    public static BuffetResponseDto toResponseDto(Buffet buffet){
        if (buffet == null) return null;

        return BuffetResponseDto
                .builder()
                .id(buffet.getId())
                .nome(buffet.getNome())
                .build();
    }

    public static Buffet toEntity(BuffetRequestDto dto){
        if (dto == null) return null;

        return Buffet
                .builder()
                .nome(dto.getNome())
                .build();
    }
}
