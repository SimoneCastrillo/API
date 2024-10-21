package buffet.app_web.mapper;

import buffet.app_web.dto.request.orcamento.OrcamentoRequestDto;
import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.dto.response.orcamento.OrcamentoResponseDto;
import buffet.app_web.entities.Decoracao;
import buffet.app_web.entities.Orcamento;
import buffet.app_web.entities.TipoEvento;
import buffet.app_web.entities.Usuario;

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
                .build();

        return orcamento;
    }

    public static OrcamentoResponseDto toResponseDto(Orcamento orcamento){
        if(orcamento == null) return null;

        TipoEvento tipoEvento = orcamento.getTipoEvento();
        Usuario usuario = orcamento.getUsuario();
        Decoracao decoracao = orcamento.getDecoracao();

        DecoracaoResponseDto.TipoEventoDto tipoEventoDecoracaoDto = DecoracaoResponseDto.TipoEventoDto
                .builder()
                .id(decoracao.getTipoEvento().getId())
                .nome(decoracao.getTipoEvento().getNome())
                .build();

        OrcamentoResponseDto.TipoEventoDto tipoEventoDto = OrcamentoResponseDto.TipoEventoDto
                .builder()
                .id(tipoEvento.getId())
                .nome(tipoEvento.getNome())
                .build();

        OrcamentoResponseDto.UsuarioDto usuarioDto = OrcamentoResponseDto.UsuarioDto
                .builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .telefone(usuario.getTelefone())
                .foto(usuario.getFoto())
                .build();

        OrcamentoResponseDto.DecoracaoDto decoracaoDto = OrcamentoResponseDto.DecoracaoDto
                .builder()
                .id(decoracao.getId())
                .nome(decoracao.getNome())
                .tipoEvento(tipoEventoDecoracaoDto)
                .build();

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
                .tipoEvento(tipoEventoDto)
                .usuario(usuarioDto)
                .decoracao(decoracaoDto)
                .build();

        return dto;
    }
}
