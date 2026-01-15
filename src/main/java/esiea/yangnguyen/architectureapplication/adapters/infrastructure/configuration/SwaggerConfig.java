package esiea.yangnguyen.architectureapplication.adapters.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI Api() {
        final String jwtSchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("🎯 Management API")
                        .description("""
                                API REST complète pour la gestion des produits.
                                **Fonctionnalités** :
                                - CRUD complet produits
                                - Recherche avancée
                                - Gestion stock & statut
                                
                                **🔐 Authentification** :
                                1. POST /login → obtenir JWT token
                                2. Swagger UI → Authorize → Bearer <token>
                                """)
                        .version("1.0.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))

                .components(new Components()
                        .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT Token: Bearer eyJhbGciOiJIUzI1NiIs...")))

                .addSecurityItem(new SecurityRequirement().addList(jwtSchemeName));
    }
}

