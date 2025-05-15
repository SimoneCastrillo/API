package buffet.app_web.util.email;

import buffet.app_web.entities.Orcamento;

import java.time.format.DateTimeFormatter;

public class ConstroiMensagensEmail {

    public static String construirMensagemOrcamentoCriado(Orcamento orcamento) {
        String decoracao = orcamento.getDecoracao() != null ? orcamento.getDecoracao().getNome() : "Sem decoração";

        return """
            <div style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #4CAF50;">Orçamento Recebido!</h2>
                <p>Olá, <strong>%s</strong>!</p>
                <p>Seu orçamento foi solicitado com sucesso e em breve entraremos em contato para alinhar os detalhes.</p>
                
                <h3>Dados do Orçamento:</h3>
                <ul>
                    <li><strong>Buffet:</strong> %s</li>
                    <li><strong>Data do Evento:</strong> %s</li>
                    <li><strong>Número de Convidados:</strong> %d</li>
                    <li><strong>Tipo de Evento:</strong> %s</li>
                    <li><strong>Decoração:</strong> %s</li>
                    <li><strong>Sugestão:</strong> %s</li>
                </ul>
            </div>
        """.formatted(
                orcamento.getUsuario().getNome(),
                orcamento.getBuffet().getNome(),
                formatarData(orcamento),
                orcamento.getQtdConvidados(),
                orcamento.getTipoEvento().getNome(),
                decoracao,
                orcamento.getSugestao()
        );
    }

    public static String construirMensagemOrcamentoConfirmado(Orcamento orcamento) {
        String decoracao = orcamento.getDecoracao() != null ? orcamento.getDecoracao().getNome() : "Sem decoração";

        return """
            <div style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #2196F3;">Orçamento Confirmado!</h2>
                <p>Olá, <strong>%s</strong>!</p>
                <p>Seu orçamento para o dia <strong>%s</strong> foi <strong>confirmado</strong>! Desejamos uma ótima festa!</p>
                
                <h3>Dados do Orçamento:</h3>
                <ul>
                    <li><strong>Número de Convidados:</strong> %d</li>
                    <li><strong>Tipo de Evento:</strong> %s</li>
                    <li><strong>Decoração:</strong> %s</li>
                    <li><strong>Sugestão:</strong> %s</li>
                </ul>
            </div>
        """.formatted(
                orcamento.getUsuario().getNome(),
                formatarData(orcamento),
                orcamento.getQtdConvidados(),
                orcamento.getTipoEvento().getNome(),
                decoracao,
                orcamento.getSugestao()
        );
    }

    public static String construirMensagemOrcamentoCancelado(Orcamento orcamento) {
        return """
            <div style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #f44336;">Orçamento Cancelado</h2>
                <p>Olá, <strong>%s</strong>!</p>
                <p>Seu orçamento para o dia <strong>%s</strong> foi <strong>cancelado</strong>. Esperamos ter outra oportunidade de atendê-lo futuramente.</p>
            </div>
        """.formatted(
                orcamento.getUsuario().getNome(),
                formatarData(orcamento)
        );
    }

    public static String construirMensagemCodigo(String codigo) {
        return """
            <div style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #9C27B0;">Código de Verificação</h2>
                <p>Use o código abaixo para confirmar sua ação:</p>
                <div style="font-size: 24px; font-weight: bold; color: #E91E63;">%s</div>
            </div>
        """.formatted(codigo);
    }

    private static String formatarData(Orcamento orcamento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return orcamento.getDataEvento().format(formatter);
    }
}
