package Pcanteen.Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // List each allowed origin separately — do NOT combine with commas
        cfg.setAllowedOriginPatterns(List.of(
            "http://localhost:3000",
            "https://resplendent-swan-b5aa25.netlify.app"  // <-- your Netlify site
        ));

        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","X-User","Accept","Origin","X-Requested-With"));
        cfg.setExposedHeaders(List.of("Authorization","Content-Type"));

        // You’re using JWT in Authorization header, not cookies:
        cfg.setAllowCredentials(false);

        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
