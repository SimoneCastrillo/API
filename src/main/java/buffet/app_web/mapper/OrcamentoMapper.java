package buffet.app_web.mapper;

import buffet.app_web.dto.request.orcamento.OrcamentoRequestDto;
import buffet.app_web.dto.response.orcamento.OrcamentoResponseDto;
import buffet.app_web.entities.Orcamento;

public class OrcamentoMapper {
    public static Orcamento toEntity(OrcamentoRequestDto dto){
        if (dto == null) return null;

        Orcamento orcamento = Orcamento
                .builder()
                .dataEvento(dto.getDataEvento())
                .qtdConvidados(dto.getQtdConvidados())
                .status("ABERTO")
                .cancelado(false)
                .inicio(dto.getInicio())
                .fim(dto.getFim())
                .sugestao(dto.getSugestao())
                .tipoEvento(dto.getTipoEvento())
                .usuario(dto.getUsuario())
                .decoracao(dto.getDecoracao())
                .build();

        return orcamento;
    }

    public static OrcamentoResponseDto toResponseDto(Orcamento orcamento){
        if(orcamento == null) return null;

        OrcamentoResponseDto dto = OrcamentoResponseDto
                .builder()
                .id(orcamento.getId())
                .dataEvento(orcamento.getDataEvento())
                .qtdConvidados(orcamento.getQtdConvidados())
                .status(orcamento.getStatus())
                .cancelado(orcamento.getCancelado())
                .inicio(orcamento.getInicio())
                .fim(orcamento.getFim())
                .sugestao(orcamento.getSugestao())
                .tipoEvento(orcamento.getTipoEvento())
                .usuario(orcamento.getUsuario())
                .decoracao(orcamento.getDecoracao())
                .build();

        return dto;
    }
}
