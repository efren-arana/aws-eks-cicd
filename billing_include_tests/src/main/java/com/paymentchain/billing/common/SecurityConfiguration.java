/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.common;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

/**
 * *
 * @author earana
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
private Environment env;
    private static final String[] NO_AUTH_LIST = {
        "*/api-docs/**",//
        "/swagger-ui/**",//
        "/configuration/ui", //
        "/swagger-resources", //
        "/configuration/security", //   
        "/webjars/**", //
        "/login",
        "/h2-console/**"};

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("qwerty"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())
                 .formLogin(form -> form
                .defaultSuccessUrl("/swagger-ui/index.html", true) // Redirigir a Swagger después del inicio de sesión exitoso
                .permitAll() // Permitir acceso a la página de inicio de sesión
               )
                .authorizeHttpRequests((authz) -> {
                    // Use lambda and stream to create AntPathRequestMatcher instances
                    authz.requestMatchers(Stream.of(NO_AUTH_LIST)
                            .map(AntPathRequestMatcher::new)
                            .toArray(AntPathRequestMatcher[]::new))
                            .permitAll();
                    authz.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable())
                .cors(corsCustomizer()) // Use the custom CorsCustomizer
                //Use default login form, but custom success redirect view
                ;
        return http.build();
    }

    @Bean
    public Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return (cors) -> {
            CorsConfiguration cc = new CorsConfiguration();
            cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
            cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
            cc.setAllowCredentials(Boolean.TRUE);
            //if you use AllowCredentials to true, you nee use AllowedOriginPatterns for allow any origin (*), because AllowedOrigin are no compatible with AllowCredentials
            cc.setAllowedOriginPatterns(Arrays.asList("*"));
            cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
            cc.setMaxAge(Duration.ZERO);
            cors.configurationSource(request -> cc);
        };
    }

}
