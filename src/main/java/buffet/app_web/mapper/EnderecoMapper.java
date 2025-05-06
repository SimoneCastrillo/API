package buffet.app_web.mapper;

import buffet.app_web.dto.request.endereco.EnderecoRequestDto;
import buffet.app_web.dto.response.endereco.EnderecoResponseDto;
import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.Endereco;

public class EnderecoMapper {

    public static EnderecoResponseDto toResponseDto(Endereco endereco) {
        if (endereco == null) return null;

        Buffet buffet = endereco.getBuffet();

        EnderecoResponseDto.BuffetDto buffetDto = EnderecoResponseDto.BuffetDto
                .builder()
                .id(buffet.getId())
                .nome(buffet.getNome())
                .email(buffet.getEmail())
                .imagem(buffet.getImagem())
                .plano(buffet.getPlano())
                .urlSite(buffet.getUrlSite())
                .telefone(buffet.getTelefone())
                .build();

        return EnderecoResponseDto.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .buffetId(buffetDto)
                .build();
    }

    public static Endereco toEntity(EnderecoRequestDto dto) {
        if (dto == null) return null;

        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }
}
