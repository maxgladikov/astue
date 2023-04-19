package astue.security;


import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthFilter;
	

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        						http.cors().disable()
//        						.csrf().disable()
//        						.authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/**", "/", "/webjars/**", "/css/**", "/js/**", "/images/**", "/api/v1/report/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement(management -> management
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//																					;
//		return http.build();
		
		return http.authorizeHttpRequests(auth -> {
						auth.requestMatchers("/","/api/v1/auth/**",  "/webjars/**", "/css/**", "/js/**", "/images/**", "/api/v1/report/**").permitAll();
						auth.anyRequest().authenticated();
					})
					.csrf(csrf -> csrf.disable())
					.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	               .authenticationProvider(authenticationProvider)
	               .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
	               .build();
	};
	

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
}
