package buffet.app_web.mapper;

import buffet.app_web.dto.request.avaliacao.AvaliacaoRequestDto;
import buffet.app_web.dto.response.avaliacao.AvaliacaoResponseDto;
import buffet.app_web.entities.Avaliacao;
import buffet.app_web.entities.TipoEvento;

import java.util.Base64;

public class AvaliacaoMapper {
    public static AvaliacaoResponseDto toResponseDto(Avaliacao avaliacao){
        if (avaliacao == null) return null;

        TipoEvento tipoEvento = avaliacao.getTipoEvento();

        AvaliacaoResponseDto.TipoEventoDto dto = AvaliacaoResponseDto.TipoEventoDto
                .builder()
                .nome(tipoEvento.getNome())
                .id(tipoEvento.getId())
                .build();

        return AvaliacaoResponseDto
                .builder()
                .id(avaliacao.getId())
                .nomeCliente(avaliacao.getNomeCliente())
                .texto(avaliacao.getTexto())
                .foto(avaliacao.getFoto())
                .tipoEvento(dto)
                .build();
    }

    public static Avaliacao toEntity(AvaliacaoRequestDto dto){
        if (dto == null) return null;

        String base64Image = null;


        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            try {

                base64Image = Base64.getEncoder().encodeToString(dto.getFoto().getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter a imagem para Base64", e);
            }
        }

        return Avaliacao
                .builder()
                .nomeCliente(dto.getNomeCliente())
                .texto(dto.getTexto())
                .foto(base64Image)
                .build();
    }
}
