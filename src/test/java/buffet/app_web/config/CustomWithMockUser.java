package buffet.app_web.config;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;


@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomWithMockUserSecurityContextFactory.class)

public @interface CustomWithMockUser {
    String username() default "user";
    String password() default "password";
    String[] authorities() default {"USER"};
}
