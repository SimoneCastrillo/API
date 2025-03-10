package buffet.app_web.util.email;

import buffet.app_web.entities.Orcamento;
import buffet.app_web.entities.Usuario;

import java.time.format.DateTimeFormatter;

public class ConstroiMensagensEmail {
    public static String construirMensagemOrcamentoCriado(Orcamento orcamento){
        String decoracao = orcamento.getDecoracao() != null ? orcamento.getDecoracao().getNome() : "Sem decoração";

        return """
            Olá, %s! Seu orçamento foi solicitado e em breve entraremos em contato
            para alinharmos os detalhes.
            
            Dados do orçamento:
             - Data do evento: %s
             - Número de Convidados: %d
             - Tipo de Evento: %s
             - Decoração: %s
             - Sugestão: %s
            """.formatted(
                    orcamento.getUsuario().getNome(),
                    formatarData(orcamento),
                    orcamento.getQtdConvidados(),
                    orcamento.getTipoEvento().getNome(),
                    decoracao,
                    orcamento.getSugestao()
        );
    }

    public static String construirMensagemOrcamentoConfirmado(Orcamento orcamento){
        String decoracao = orcamento.getDecoracao() != null ? orcamento.getDecoracao().getNome() : "Sem decoração";

        return  """
               Olá, %s! Seu orçamento do dia %s foi confirmado! Desejamos boa festa a você e seus convidados!
               
               Dados do orçamento:
                - Número de Convidados: %d
                - Tipo de Evento: %s
                - Decoracao: %s
                - Sugestão: %s
               """.formatted(
                orcamento.getUsuario().getNome(),
                formatarData(orcamento),
                orcamento.getQtdConvidados(),
                orcamento.getTipoEvento().getNome(),
                decoracao,
                orcamento.getSugestao());
    }

    public static String construirMensagemOrcamentoCancelado(Orcamento orcamento){
        return  """
               Olá, %s! Seu orçamento do dia %s foi cancelado. Esperamos entrar em contato novamente em outra oportunidade.
               """.formatted(
                orcamento.getUsuario().getNome(),
                formatarData(orcamento));
    }

    public static String construirMensagemCodigo(String codigo){
        return  """
               O código de confirmação é: %s
               """.formatted(
                       codigo
        );
    }

    private static String formatarData(Orcamento orcamento){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return orcamento.getDataEvento().format(formatter);
    }
}
