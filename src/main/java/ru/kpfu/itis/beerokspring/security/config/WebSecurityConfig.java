package ru.kpfu.itis.beerokspring.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private static final String[] PERMIT_ALL = {"/beer/list", "/beer/detail/**", "/post/list", "/post/detail/**",
            "/search", "/searching/**", "/main/**", "/account/someone/**", "/registration", "/verify", "/sign-in"
    };

    private static final String[] ADMIN = {"/admin/**", "/beer/add", "/beer/edit", "/beer/delete", "/post/deleteAdmin"
    };

    private static final String[] IGNORE = {"/WEB-INF/jsp/**", "/style/**", "/js/**", "/uploads/**"
    };

    private final UserDetailsService userDetailsService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(IGNORE);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PERMIT_ALL).permitAll()
                        .requestMatchers(ADMIN).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/sign-in")
                                .loginProcessingUrl("/sign-in")
                                .defaultSuccessUrl("/account/profile", false)
                                .failureUrl("/sign-in?error=true")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/sign-out")
                                .logoutSuccessUrl("/sign-in")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                )
                .rememberMe((rememberMe) ->
                        rememberMe
                                .rememberMeParameter("remember")
                                .key("uniqueAndSecret")
                                .tokenValiditySeconds(60 * 60 * 12)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptions ->
                        exceptions
                                .accessDeniedPage("/no-access")
                )
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}

