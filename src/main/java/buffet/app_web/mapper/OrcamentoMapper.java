package buffet.app_web.mapper;

import buffet.app_web.dto.request.orcamento.OrcamentoConfirmacaoDto;
import buffet.app_web.dto.request.orcamento.OrcamentoRequestDto;
import buffet.app_web.dto.response.decoracao.DecoracaoResponseDto;
import buffet.app_web.dto.response.orcamento.OrcamentoResponseDto;
import buffet.app_web.entities.*;

public class OrcamentoMapper {
    public static Orcamento toEntity(OrcamentoRequestDto dto){
        if (dto == null) return null;

        Buffet buffet = Buffet
                .builder()
                .id(dto.getBuffetId())
                .build();

        Orcamento orcamento = Orcamento
                .builder()
                .dataEvento(dto.getDataEvento())
                .qtdConvidados(dto.getQtdConvidados())
                .status("PENDENTE")
                .cancelado(false)
                .inicio(dto.getInicio())
                .fim(dto.getInicio().plusHours(2))
                .sugestao(dto.getSugestao())
                .build();

        return orcamento;
    }

    public static Orcamento toEntity(OrcamentoConfirmacaoDto dto){
        if (dto == null) return null;

        Orcamento orcamento = Orcamento
                .builder()
                .dataEvento(dto.getDataEvento())
                .qtdConvidados(dto.getQtdConvidados())
                .inicio(dto.getInicio())
                .fim(dto.getFim())
                .saborBolo(dto.getSaborBolo())
                .pratoPrincipal(dto.getPratoPrincipal())
                .lucro(dto.getFaturamento() - dto.getDespesa())
                .faturamento(dto.getFaturamento())
                .despesa(dto.getDespesa())
                .sugestao(dto.getSugestao())
                .build();

        return orcamento;
    }

    public static OrcamentoResponseDto toResponseDto(Orcamento orcamento) {
        if (orcamento == null) return null;

        TipoEvento tipoEvento = orcamento.getTipoEvento();
        Usuario usuario = orcamento.getUsuario();
        Decoracao decoracao = orcamento.getDecoracao();
        Buffet buffet = orcamento.getBuffet();
        Endereco endereco = orcamento.getEndereco();

        DecoracaoResponseDto.TipoEventoDto tipoEventoDecoracaoDto = null;
        if (decoracao != null) {
            tipoEventoDecoracaoDto = DecoracaoResponseDto.TipoEventoDto
                    .builder()
                    .id(decoracao.getTipoEvento().getId())
                    .nome(decoracao.getTipoEvento().getNome())
                    .build();
        }

        OrcamentoResponseDto.BuffetDto buffetDto = OrcamentoResponseDto.BuffetDto
                .builder()
                .id(buffet.getId())
                .nome(buffet.getNome())
                .email(buffet.getEmail())
                .descricao(buffet.getDescricao())
                .urlSite(buffet.getUrlSite())
                .imagem(buffet.getImagem())
                .telefone(buffet.getTelefone())
                .plano(buffet.getPlano())
                .build();

        OrcamentoResponseDto.EnderecoDto enderecoDto = OrcamentoResponseDto.EnderecoDto
                .builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .buffetId(endereco.getBuffet().getId())
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
                .telefone(usuario.getTelefone())
                .buffetId(usuario.getBuffet().getId())
                .build();

        OrcamentoResponseDto.DecoracaoDto decoracaoDto = null;
        if (decoracao != null) {
            decoracaoDto = OrcamentoResponseDto.DecoracaoDto
                    .builder()
                    .id(decoracao.getId())
                    .nome(decoracao.getNome())
                    .tipoEvento(tipoEventoDecoracaoDto)
                    .build();
        }

        OrcamentoResponseDto dto = OrcamentoResponseDto
                .builder()
                .id(orcamento.getId())
                .dataEvento(orcamento.getDataEvento())
                .qtdConvidados(orcamento.getQtdConvidados())
                .status(orcamento.getStatus())
                .cancelado(orcamento.getCancelado())
                .inicio(orcamento.getInicio())
                .fim(orcamento.getFim())
                .saborBolo(orcamento.getSaborBolo())
                .pratoPrincipal(orcamento.getPratoPrincipal())
                .lucro(orcamento.getLucro())
                .faturamento(orcamento.getFaturamento())
                .despesa(orcamento.getDespesa())
                .sugestao(orcamento.getSugestao())
                .googleEventoId(orcamento.getGoogleEventoId())
                .tipoEvento(tipoEventoDto)
                .usuario(usuarioDto)
                .decoracao(decoracaoDto)
                .buffet(buffetDto)
                .endereco(enderecoDto)
                .build();

        return dto;
    }

}
