package buffet.app_web.controllers;

import buffet.app_web.configuration.Google;
import buffet.app_web.service.GoogleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("api/eventos")
public class GoogleController {
    @Autowired
    private GoogleService googleService;

    @Operation(summary = "Listar Todos os Eventos", description = """
        # Listar Eventos
        ---
        Retorna uma lista de todos os eventos disponíveis.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos listados com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Google.class))
                    )
            ),
    })
    @GetMapping
    public List<Google> listarTodos() throws GeneralSecurityException, IOException {
        return googleService.listarEventos();
    }

    @Operation(summary = "Listar Eventos em Ordem Alfabética", description = """
        # Listar Eventos
        ---
        Retorna uma lista de eventos em ordem alfabética.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos listados em ordem alfabética com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Google.class))
                    )
            ),
    })
    @GetMapping("/alfabetica")
    public Google[] listarOrdemAlfabetica() throws GeneralSecurityException, IOException {
        return googleService.listarEventosAlfabetica();
    }

    @Operation(summary = "Pesquisa Binária", description = """
        # Pesquisa Binária
        ---
        Realiza uma pesquisa binária em eventos com base no resumo fornecido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado da pesquisa binária retornado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content()
            ),
    })
    @GetMapping("/pesquisa")
    public int pesquisaBinaria(@RequestParam String summary) throws GeneralSecurityException, IOException {
        return googleService.pesquisaBinaria(listarOrdemAlfabetica(), summary);
    }
}
