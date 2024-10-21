package buffet.app_web.mapper;

import buffet.app_web.dto.request.decoracao.DecoracaoRequestDto;
import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.TipoEvento;

import java.util.Base64;

public class DecoracaoMapper {
    public static Decoracao toEntity(DecoracaoRequestDto dto) {
        if (dto == null) return null;

        String base64Image = null;


        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            try {

                base64Image = Base64.getEncoder().encodeToString(dto.getFoto().getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter a imagem para Base64", e);
            }
        }


        Decoracao decoracao = Decoracao.builder()
                .nome(dto.getNome())
                .foto(base64Image)
                .build();

        return decoracao;
    }

    public static DecoracaoResponseDto toResponseDto(Decoracao decoracao){
        if (decoracao == null) return null;

        TipoEvento tipoEvento = decoracao.getTipoEvento();

        DecoracaoResponseDto.TipoEventoDto dto = DecoracaoResponseDto.TipoEventoDto
                .builder()
                .id(tipoEvento.getId())
                .nome(tipoEvento.getNome())
                .build();

        DecoracaoResponseDto decoracaoResponseDto = DecoracaoResponseDto.builder()
                .id(decoracao.getId())
                .nome(decoracao.getNome())
                .foto(decoracao.getFoto())
                .tipoEvento(dto)
                .build();
        return decoracaoResponseDto;
    }
}
