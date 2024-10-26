package buffet.app_web.service;

import buffet.app_web.dto.response.orcamento.OrcamentoResponseDto;
import buffet.app_web.mapper.OrcamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExportacaoService {
    @Autowired
    private OrcamentoService orcamentoService;

    public void exportarOrcamento(){
        try {
            OutputStream outputStream = new FileOutputStream("orcamentos.csv");

            BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
            );

            escritor.write("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n".formatted(
                    "id",
                    "dataEvento",
                    "qtdConvidados",
                    "status",
                    "cancelado",
                    "inicio",
                    "fim",
                    "sugestao",
                    "fkTipoEvento",
                    "fkUsuario",
                    "fkDecoracao"));

            List<OrcamentoResponseDto> list =
                    orcamentoService.listarTodos().stream().map(OrcamentoMapper::toResponseDto).toList();

            for (OrcamentoResponseDto dto : list){
                escritor.write("%d;%s;%d;%s;%s;%s;%s;%s;%d;%d;%d\n".formatted(
                        dto.getId(),
                        dto.getDataEvento(),
                        dto.getQtdConvidados(),
                        dto.getStatus(),
                        dto.getCancelado(),
                        dto.getInicio(),
                        dto.getFim(),
                        dto.getSugestao(),
                        dto.getTipoEvento().getId(),
                        dto.getUsuario().getId(),
                        dto.getDecoracao().getId()
                ));
            }

            escritor.close();
            outputStream.close();

        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo");
        }
    }
}
