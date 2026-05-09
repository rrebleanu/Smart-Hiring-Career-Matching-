package com.project.demo.config;

import com.project.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.MediaType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Lista de URL-uri publice care nu necesita autentificare
                        .requestMatchers(
                                "/",
                                "/anunturi",
                                "/despre",
                                "/contact",
                                "/register",
                                "/login",
                                "/register/candidat", "/register/angajator",
                                "/css/**", "/js/**", "/images/**"
                                  // Adaugat /** pentru a prinde toate sub-path-urile
                        ).permitAll()
                        // Configurarea accesului bazat pe roluri
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/cv/download/**").authenticated()
                        .requestMatchers("/angajator/**").hasRole("ANGAJATOR")
                        .requestMatchers("/aplica/**").hasAnyRole("CANDIDAT", "ADMIN")
                        .requestMatchers("/dashboard/**").hasRole("CANDIDAT")
                        .requestMatchers("/uploads/**").authenticated()
                        .requestMatchers("/new").authenticated()
                        // Toate celelalte cereri necesita autentificare
                        .anyRequest().authenticated()
                )
                // Configurarea procesului de login
                .formLogin(form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("parola")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )

                // Configurarea procesului de logout

                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout=true")
//                        .logoutSuccessUrl("/custom-login?logout")
                                .invalidateHttpSession(true)
                                // Sterge cookie ul de sesiune la logout
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // dezactivezi CSRF doar pentru API REST daca ai
                );

        return http.build();
    }

    // Criptarea parolelor
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return builder.build();
    }
}

