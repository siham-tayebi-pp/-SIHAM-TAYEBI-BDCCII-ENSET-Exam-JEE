//package net.tayebi.sihamtayebibdcciiensetexamjeebackend.config;
//
//import com.nimbusds.jose.jwk.source.ImmutableSecret;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.util.List;
//@Configuration @EnableWebSecurity @EnableMethodSecurity(prePostEnabled = true)
//@AllArgsConstructor
//public class SecurityConfig {
//
//    @Value("${jwt.secret}") private String secretKey;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(csrf -> csrf.disable())
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(ar -> ar
//                        .requestMatchers("/auth/login/**").permitAll()
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(ors -> ors.jwt(Customizer.withDefaults()))
//                .headers(h -> h.frameOptions(fo -> fo.disable())) // pour H2 console
//                .build();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        // ── 3 rôles : ROLE_CLIENT, ROLE_EMPLOYE, ROLE_ADMIN ──
//        UserDetails roleClient = User.withUsername("client1")
//                .password("{noop}1234")
//                .authorities("ROLE_CLIENT")
//                .build();
//        UserDetails roleEmploye = User.withUsername("employe1")
//                .password("{noop}1234")
//                .authorities("ROLE_CLIENT", "ROLE_EMPLOYE")
//                .build();
//        UserDetails roleAdmin = User.withUsername("admin")
//                .password("{noop}1234")
//                .authorities("ROLE_CLIENT", "ROLE_EMPLOYE", "ROLE_ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(roleClient, roleEmploye, roleAdmin);
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder() {
//        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");
//        return new NimbusJwtEncoder(new ImmutableSecret<>(key));
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");
//        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS512).build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService uds) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(uds);
//        return new ProviderManager(provider);
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("http://localhost:4200");
//        config.addAllowedMethod("*");
//        config.addAllowedHeader("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//}