package ru.deathkiller2009.dvdspringbootproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.deathkiller2009.dvdspringbootproject.services.PersonDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(personDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.formLogin(http -> http.usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/auth/login")
                .loginProcessingUrl("/security-process")
                .defaultSuccessUrl("/person", true)
                .failureUrl("/auth/login?error")
                .permitAll()
        );
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login", "/auth/register", "/error").permitAll()
                .requestMatchers("/disks/create", "/admin/**", "/disks/*/edit").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/disks/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/disks/*").hasRole("ADMIN")
                .anyRequest().hasAnyRole("USER", "ADMIN"));
        httpSecurity.logout(logout -> logout.logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login").permitAll());
        httpSecurity.exceptionHandling(ex -> ex.accessDeniedPage("/error"));
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
