package buffet.app_web.mapper;

import buffet.app_web.dto.request.decoracao.DecoracaoRequestDto;
import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.entities.Buffet;
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
        Buffet buffet = decoracao.getBuffet();

        DecoracaoResponseDto.TipoEventoDto tipoEventoDto = DecoracaoResponseDto.TipoEventoDto
                .builder()
                .id(tipoEvento.getId())
                .nome(tipoEvento.getNome())
                .build();

        DecoracaoResponseDto.BuffetResponseDto buffetDto = DecoracaoResponseDto.BuffetResponseDto
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

        DecoracaoResponseDto decoracaoResponseDto = DecoracaoResponseDto.builder()
                .id(decoracao.getId())
                .nome(decoracao.getNome())
                .foto(decoracao.getFoto())
                .tipoEvento(tipoEventoDto)
                .buffet(buffetDto)
                .build();

        return decoracaoResponseDto;
    }
}
