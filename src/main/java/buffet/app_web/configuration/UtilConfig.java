package buffet.app_web.configuration;

import buffet.app_web.util.FilaObj;
import buffet.app_web.util.PilhaObj;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean
    public PilhaObj pilhaObj() {
        return new PilhaObj(20);
    }

    @Bean
    public FilaObj filaObj() {
        return new FilaObj(50);
    }
}
