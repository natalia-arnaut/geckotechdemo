package com.geckotech.assessment.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Value("1")
    String version;

    @Bean
    public OpenAPI docs() {
        return new OpenAPI()
            .info(apiInfo())
            .components(securitySchemas());
    }

    public Components securitySchemas() {
        return new Components();
    }

    private Info apiInfo() {
        return new Info().title("Demo Service")
            .description("Service description")
            .version(version);
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
