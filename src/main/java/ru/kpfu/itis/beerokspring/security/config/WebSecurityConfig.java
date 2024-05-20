package ru.kpfu.itis.beerokspring.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private static final String[] PERMIT_ALL = {"/beer/list", "/beer/detail/**", "/post/list", "/post/detail/**",
            "/search", "/searching/**", "/main/**", "/account/someone/**", "/registration", "/verify"
    };

    private static final String[] ADMIN = {"/admin/**", "/beer/add", "/beer/edit", "/beer/delete"
    };

    private static final String[] IGNORE = {"/WEB-INF/jsp/**", "/style/**", "/js/**", "/uploads/**"
    };

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
                                .defaultSuccessUrl("/account/profile")
                                .permitAll()
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
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public HttpFirewall customHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }
}

