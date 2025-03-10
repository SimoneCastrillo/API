package buffet.app_web.util.email;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class GeradorCodigoVerificacao {
    private static final String NUMEROS = "0123456789";
    private static final int COMPRIMENTO_CODIGO = 6;
    private static final SecureRandom random = new SecureRandom();
    private static final Map<String, String> codigosVerificacao = new HashMap<>();


    public static String gerarCodigoVerificacao() {
        StringBuilder codigo = new StringBuilder(COMPRIMENTO_CODIGO);

        for (int i = 0; i < COMPRIMENTO_CODIGO; i++) {
            codigo.append(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        }

        return codigo.toString();
    }

    public static void armazenarCodigo(String email, String code) {
        codigosVerificacao.put(email, code);
    }

    public static String getCodigo(String email) {
        return codigosVerificacao.get(email);
    }

    public static void removerCodigo(String email) {
        codigosVerificacao.remove(email);
    }
}
