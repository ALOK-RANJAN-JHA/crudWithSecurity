package com.crud.config;


import com.crud.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final SessionExpiryFilter sessionExpiryFilter;
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
              .anyRequest().authenticated()
          )
          .formLogin(form -> form
              .loginPage("/login")
              .loginProcessingUrl("/login")
              .defaultSuccessUrl("/employees", true)
              .failureUrl("/login?error=true")
              .permitAll()
          )
          .rememberMe(r -> r
              .key("super-secret-remember-me-key")
              .tokenValiditySeconds(7 * 24 * 60 * 60)
              .rememberMeParameter("remember-me")
              .userDetailsService(userDetailsService)
              .tokenRepository(persistentTokenRepository())
          )
          .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/login?logout=true")
              .invalidateHttpSession(true)
              .deleteCookies("JSESSIONID", "remember-me")
              .permitAll()
          )
          .sessionManagement(session -> session
              .invalidSessionUrl("/login?expired=true")
          )
          .addFilterBefore(sessionExpiryFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
          .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);

        return repo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
