package buffet.app_web.controllers;

import buffet.app_web.entities.Endereco;
import buffet.app_web.entities.EnderecoApiExterna;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Operações relacionadas a endereços")
public class EnderecoController {
    Endereco enderecos[] = new Endereco[9];
    int tamanhoAtual = 0;

    private static final Logger log = LoggerFactory.getLogger(EnderecoController.class);

    @Operation(summary = "Buscar endereço por CEP", description = """
            # Buscar endereço
            ---
            Retorna o endereço correspondente ao CEP fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Endereco.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum endereço encontrado para o CEP",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<Endereco> buscarEndereco(@RequestParam String cep) {

        RestClient client = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();

        String raw = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(String.class);

        log.info("Resposta da API: " + raw);

        EnderecoApiExterna endereco = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(EnderecoApiExterna.class);

        if (endereco == null) {
            return ResponseEntity.noContent().build();
        }

        Endereco resposta = new Endereco();
        resposta.setBairro(endereco.getBairro());
        resposta.setCep(endereco.getCep());
        resposta.setCidade(endereco.getCidade());
        resposta.setEstado(endereco.getEstado());
        resposta.setRua(endereco.getRua());

        try {
            enderecos[tamanhoAtual] = resposta;

            ordenarPorCep(enderecos, tamanhoAtual);

            for (int i = 0; i <= tamanhoAtual; i++) {
                System.out.println(enderecos[i].getCep());
            }
            tamanhoAtual++;

        } catch (Exception e){
            System.out.println("Excedido o número de requisições");
        }

        System.out.println(Arrays.toString(enderecos));
        return ResponseEntity.ok(resposta);
    }


    public static void ordenarPorCep(Endereco[] vetor, int tamanhoAtual){
        for (int i = 1; i <= tamanhoAtual; i++) {
            Endereco x = vetor[i];
            int j = i - 1;

            while ((j >= 0) && (vetor[j].getCep().compareTo(x.getCep()) > 0)){
                vetor[j + 1] = vetor[j];
                j = j - 1;
            }
            vetor[j + 1] = x;
        }
    }

    @Operation(summary = "Exibir dados de endereços ordenados", description = """
            # Dados de endereços ordenados
            ---
            Retorna a lista de endereços ordenados pelo CEP.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços ordenados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Endereco[].class)
                    )
            )
    })
    @GetMapping("/dados-ordenados")
    public Endereco[] exibirDadosOrdenados(){
        return enderecos;
    }
}
