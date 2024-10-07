package buffet.app_web.mapper;

import buffet.app_web.dto.request.avaliacao.AvaliacaoRequestDto;
import buffet.app_web.dto.response.avaliacao.AvaliacaoResponseDto;
import buffet.app_web.entities.Avaliacao;

public class AvaliacaoMapper {
    public static AvaliacaoResponseDto toResponseDto(Avaliacao avaliacao){
        if (avaliacao == null) return null;

        return AvaliacaoResponseDto
                .builder()
                .id(avaliacao.getId())
                .nomeCliente(avaliacao.getNomeCliente())
                .texto(avaliacao.getTexto())
                .tipoEvento(avaliacao.getTipoEvento())
                .build();
    }

    public static Avaliacao toEntity(AvaliacaoRequestDto dto){
        if (dto == null) return null;

        return Avaliacao
                .builder()
                .nomeCliente(dto.getNomeCliente())
                .texto(dto.getTexto())
                .tipoEvento(dto.getTipoEvento())
                .build();
    }
}
