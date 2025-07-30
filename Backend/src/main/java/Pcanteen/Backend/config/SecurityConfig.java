package Pcanteen.Backend.config;

import Pcanteen.Backend.security.CustomUserDetailsService;
import Pcanteen.Backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            // Use the CORS configuration defined in your separate CorsConfig
            .cors(Customizer.withDefaults())

            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                // Public health/root & Swagger
                .requestMatchers("/", "/health", "/error").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // Public auth endpoints
                .requestMatchers("/api/auth/**").permitAll()

                // Public data you allowed
                .requestMatchers("/api/menu/**", "/api/orders/**", "/api/transactions/**").permitAll()

                // Admin-only
                // NOTE: If your GrantedAuthorities are "ADMIN"/"SUPER_ADMIN" (no "ROLE_" prefix),
                // use hasAnyAuthority(...). If they are "ROLE_ADMIN"/"ROLE_SUPER_ADMIN", use hasAnyRole(...).
                .requestMatchers("/api/admin/**",
                                 "/api/feedback/notifications/create",
                                 "/api/feedback/suggestions/all",
                                 "/api/feedback/suggestions/respond/**")
                    //.hasAnyRole("ADMIN", "SUPER_ADMIN")   // use if your authorities have "ROLE_" prefix
                    .hasAnyAuthority("ADMIN", "SUPER_ADMIN") // use if no "ROLE_" prefix

                // User or admin
                .requestMatchers("/api/feedback/notifications/my",
                                 "/api/feedback/suggestions/create",
                                 "/api/feedback/suggestions/my")
                    //.hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                    .hasAnyAuthority("USER", "ADMIN", "SUPER_ADMIN")

                .anyRequest().authenticated()
            )

            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
