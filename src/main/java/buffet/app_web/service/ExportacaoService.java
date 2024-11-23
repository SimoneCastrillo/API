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

    private static final String CSV_FILE_PATH = "orcamentos.csv";

    public void exportarOrcamento(){
        try {
            OutputStream outputStream = new FileOutputStream(CSV_FILE_PATH);

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
                    "tipoEvento",
                    "telefoneCliente",
                    "nomeCliente"));

            List<OrcamentoResponseDto> list =
                    orcamentoService.listarTodos().stream().map(OrcamentoMapper::toResponseDto).toList();

            for (OrcamentoResponseDto dto : list){
                escritor.write("%d;%s;%d;%s;%s;%s;%s;%s;%s;%s;%s\n".formatted(
                        dto.getId(),
                        dto.getDataEvento(),
                        dto.getQtdConvidados(),
                        dto.getStatus(),
                        dto.getCancelado(),
                        dto.getInicio(),
                        dto.getFim(),
                        dto.getSugestao(),
                        dto.getTipoEvento().getNome(),
                        dto.getUsuario().getTelefone(),
                        dto.getUsuario().getNome()
                ));
            }

            escritor.close();
            outputStream.close();

        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo");
        }
    }

    public String getCsvFilePath() {
        return CSV_FILE_PATH;
    }
}
