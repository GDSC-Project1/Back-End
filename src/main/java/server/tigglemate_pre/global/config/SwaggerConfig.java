package server.tigglemate_pre.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(customInfo());
    }

    private Info customInfo() {
        return new Info()
                .title("티끌 메이트(Tiggle Mate)")
                .description("스프링 가계부 어플리케이션 API 문서")
                .version("1.0.0");
    }
}
