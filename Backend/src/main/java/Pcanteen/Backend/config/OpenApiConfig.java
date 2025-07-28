package Pcanteen.Backend.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Central Swagger / OpenAPI configuration.
 * Declaring it once makes the 🔒 Authorize button appear,
 * and all endpoints pick up the bearer‑token requirement automatically.
 */
@OpenAPIDefinition(
    info = @Info(title = "My API", version = "v1"),
    security = @SecurityRequirement(name = "bearerAuth")   // applies globally
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {
    // no code needed; the annotations are enough
}

