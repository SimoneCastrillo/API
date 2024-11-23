package buffet.app_web.controllers;

import buffet.app_web.service.ExportacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/csv")
public class CsvController {
    @Autowired
    private ExportacaoService exportacaoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/download", produces = "text/csv")
    public ResponseEntity downloadCSV() throws IOException {
        exportacaoService.exportarOrcamento();

        Resource fileResource = new FileSystemResource(exportacaoService.getCsvFilePath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"dados_mockados.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(fileResource);
    }
}
