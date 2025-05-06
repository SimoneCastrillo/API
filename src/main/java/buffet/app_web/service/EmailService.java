package buffet.app_web.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmailHtml(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("dudu.castrillo@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true = é HTML

        javaMailSender.send(message);
    }


    public void enviarCodigoVerificacao(String to, String subject, String codigo) throws MessagingException {
        String html = "<div style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #2c3e50;'>Código de Verificação</h2>" +
                "<p>Seu código de verificação é:</p>" +
                "<h1 style='color: #e74c3c;'>" + codigo + "</h1>" +
                "<p>Use este código para confirmar sua identidade.</p>" +
                "</div>";

        enviarEmailHtml(to, subject, html);
    }
}
