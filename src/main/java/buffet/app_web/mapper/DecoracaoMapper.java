package buffet.app_web.mapper;

import buffet.app_web.dto.request.decoracao.DecoracaoRequestDto;
import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.entities.Decoracao;

public class DecoracaoMapper {
    public static Decoracao toEntity(DecoracaoRequestDto dto){
        Decoracao decoracao = Decoracao
                .builder()
                .nome(dto.getNome())
                .foto(dto.getFoto())
                .tipoEvento(dto.getTipoEvento())
                .build();
        return decoracao;

    }

    public static DecoracaoResponseDto toResponseDto(Decoracao decoracao){
        DecoracaoResponseDto dto = DecoracaoResponseDto.builder()
                .id(decoracao.getId())
                .nome(decoracao.getNome())
                .foto(decoracao.getFoto())
                .tipoEvento(decoracao.getTipoEvento())
                .build();
        return dto;
    }
}
